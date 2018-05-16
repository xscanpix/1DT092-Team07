package org.team7.server.robot;

import org.team7.server.robot.robotmessage.RobotMessage;
import org.team7.server.robot.robotmessage.RobotMessageException;
import org.team7.server.robot.robotmessage.RobotMessageMove;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Robot {
    private BlockingQueue<RobotMessage> messages;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    private int id;

    Robot(Socket socket) {
        id = 1;
        this.messages = new ArrayBlockingQueue<>(100);
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public void start() {
        new Thread(() -> {
            while (true) {
                try {
                    /*int len = in.readByte();

                    byte[] bytes = new byte[len];
                    {
                        int read = in.read(bytes);
                    }
                    ByteBuffer buf = ByteBuffer.allocate(len);
                    buf.put(bytes);

                    RobotMessage msg = RobotMessage.decodeMessage(buf);

                    messages.offer(msg, 100, TimeUnit.MILLISECONDS);
                    */
                    RobotMessage msg = new RobotMessageMove(id, RobotMessageMove.FORWARD);

                    ByteBuffer buf = msg.encodeMessage();

                    int len = buf.remaining();
                    byte[] bytes = buf.array();

                    out.writeByte(len);
                    out.write(bytes);

                } catch (RobotMessageException | IOException e) {
                    break;
                }
            }
        }).start();
    }

    public RobotMessage getMessage() {
        RobotMessage msg = null;

        try {
            msg = messages.poll(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return msg;
    }
}