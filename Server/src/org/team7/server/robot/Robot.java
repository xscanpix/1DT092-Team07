package org.team7.server.robot;

import org.team7.server.robot.robotmessage.RobotMessage;
import org.team7.server.robot.robotmessage.RobotMessageException;

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

    public Thread start() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    int len = in.readByte();

                    byte[] bytes = new byte[len];
                    {
                        int read = in.read(bytes);
                    }
                    ByteBuffer buf = ByteBuffer.allocate(len);
                    buf.put(bytes);

                    RobotMessage msg = RobotMessage.decodeMessage(buf);

                    messages.offer(msg);
                } catch (EOFException e) {
                    break;
                } catch (RobotMessageException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
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