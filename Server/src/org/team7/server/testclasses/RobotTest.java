package org.team7.server.testclasses;

import org.team7.server.robot.robotmessage.RobotMessage;
import org.team7.server.robot.robotmessage.RobotMessageException;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * A test class for simulating a org.team7.server.robot.
 */
public class RobotTest {

    private static int id = 1;

    private int myId;

    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    public RobotTest() {
        myId = id++;
    }

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int msBetweenSend) {
        new Thread(() -> {

            //while (true) {
            try {
                int len = in.readByte();

                byte[] bytes = new byte[len];
                {
                    int read = in.read(bytes);
                }
                ByteBuffer buf = ByteBuffer.allocate(len);
                buf.put(bytes);

                RobotMessage msg = RobotMessage.decodeMessage(buf);

                if (msg != null) {
                    System.out.println(msg);
                }

            } catch (IOException e) {
                //break;
            } catch (RobotMessageException e) {
                e.printStackTrace();
            }
            //}
            /*
            for (int i = 1; i <= 5; i++) {
                try {
                    send(RobotMessage.encodeMessage(new RobotMessage(RobotMessage.OPS.TEST.ordinal(), "Message " + i)));
                } catch (RobotMessageException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(msBetweenSend);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            */

            disconnect();
        }).start();
    }

    private void send(ByteBuffer message) {
        if (socket != null && socket.isConnected()) {
            try {
                out.writeByte(message.array().length);
                out.write(message.array());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ByteBuffer receive() {
        ByteBuffer buf = null;
        if (socket != null && socket.isConnected()) {
            try {
                int len = in.readByte();
                buf = ByteBuffer.allocate(len);
                byte[] bytes = new byte[len];
                {
                    int read = in.read(bytes);
                }
                buf.put(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buf;
    }
}
