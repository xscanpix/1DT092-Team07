package org.team7.server.robot;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

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
            System.out.println("[Robot " + myId + "] Connected to: " + socket.getLocalAddress() + " " + socket.getLocalPort());
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("[Robot " + myId + "] Disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Thread start() {
        Thread thread = new Thread(() -> {

            for (int i = 1; i <= 5; i++) {
                System.out.println("[Robot " + myId + "] Sending data to RobotControl: Message " + i);
                try {
                    send(RobotMessage.encodeMessage(new RobotMessage(RobotMessage.OPS.TEST.ordinal(), "Message " + i)));
                } catch (RobotMessageException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            disconnect();
        });

        thread.start();

        return thread;
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

    public String receive() {
        String res = null;
        if (socket != null && socket.isConnected()) {
            try {
                int len = in.readByte();
                byte[] bytes = new byte[1024];
                int read = in.read(bytes, 0, len);
                res = new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
    }
}
