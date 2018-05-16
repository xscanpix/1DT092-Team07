package org.team7.server.testclasses;

import org.team7.server.robot.robotmessage.RobotMessage;
import org.team7.server.robot.robotmessage.RobotMessageException;
import org.team7.server.robot.robotmessage.RobotMessageSetupReply;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * A test class for simulating a org.team7.server.robot.
 */
public class RobotTest {
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    private int myId;
    private int x;
    private int y;

    public RobotTest(int x, int y) {
        this.x = x;
        this.y = y;
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

            while (true) {
                try {
                    ByteBuffer buf = receive();

                    RobotMessage msg = RobotMessage.decodeMessage(buf);

                    if (msg.getOp() == RobotMessage.ops.get("SETUP")) {
                        send(new RobotMessageSetupReply((Integer) msg.values.get("ID"), x, y).encodeMessage());
                    }

                } catch (RobotMessageException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void send(ByteBuffer message) {
        try {
            out.writeByte(message.array().length);
            out.write(message.array());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer receive() {
        ByteBuffer buf = null;
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
        return buf;
    }
}
