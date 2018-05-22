package org.team7.server.robot;

import org.team7.server.robot.robotmessage.RobotMessage;
import org.team7.server.robot.robotmessage.RobotMessageException;
import org.team7.server.robot.robotmessage.RobotMessageSetup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Robot {
    private static int ROBOTID = 0;
    public int id;
    private BlockingQueue<RobotMessage> queueFromRobot;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private BlockingQueue<RobotMessage> queueToRobot;
    private int x;
    private int y;

    Robot(Socket socket) {
        id = ROBOTID++;
        this.queueFromRobot = new ArrayBlockingQueue<>(50);
        this.queueToRobot = new ArrayBlockingQueue<>(50);
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void start() {
        new Thread(() -> {
            sendMessage(new RobotMessageSetup(id).encodeMessage());

            while(true) {
                try {
                    int len;
                    byte[] bytes;
                    ByteBuffer buf;
                    RobotMessage msg;

                    len = in.readByte();

                    bytes = new byte[ len ];
                    {
                        int read = in.read(bytes);
                    }
                    buf = ByteBuffer.allocate(len);
                    buf.put(bytes);

                    msg = RobotMessage.decodeMessage(buf);

                    queueFromRobot.offer(msg, 100, TimeUnit.MILLISECONDS);

                } catch(RobotMessageException | IOException e) {
                    break;
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public RobotMessage getMessage() {
        RobotMessage msg = null;

        try {
            msg = queueFromRobot.poll(100, TimeUnit.MILLISECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public void sendMessage(ByteBuffer buf) {
        int len = buf.remaining();
        byte[] bytes = new byte[ len ];
        buf.get(bytes);

        try {
            out.writeByte(len);
            out.write(bytes);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}