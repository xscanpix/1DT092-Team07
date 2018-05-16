package org.team7.server.testclasses;

import org.team7.server.sensor.sensormessage.SensorMessage;
import org.team7.server.sensor.sensormessage.SensorMessageReadings;
import org.team7.server.sensor.sensormessage.SensorMessageSetup;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * A test class for simulating a sensor.
 */
public class SensorTest {
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    private int id;
    private int x;
    private int y;

    public SensorTest(int id, int x, int y) {
        this.id = id;
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

    public Thread start(int msBetweenSend) {
        Thread thread = new Thread(() -> {

            send(new SensorMessageSetup(id, x, y).encodeMessage());

            while (socket.isConnected()) {
                SensorMessage msg = new SensorMessageReadings(id, new Random().nextInt(), new Random().nextInt());

                send(msg.encodeMessage());

                try {
                    Thread.sleep(msBetweenSend);
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
