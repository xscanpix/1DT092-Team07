package org.team7.server.sensor;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * A test class for simulating a sensor.
 */
public class SensorTest {

    private static int id = 1;

    private int myId;

    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    public SensorTest() {
        myId = id++;
    }

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            System.out.println("[Sensor " + myId + "] Connected to: " + socket.getLocalAddress() + " " + socket.getLocalPort());
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
            System.out.println("[Sensor " + myId + "] Disconnected.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Thread start(int msBetweenSend) {
        Thread thread = new Thread(() -> {

            while (socket.isConnected()) {
                SensorMessage msg = new SensorMessage(SensorMessage.OPS.READINGS.ordinal(), 100, 100);
                System.out.println("[Sensor " + myId + "] Sending data to SensorControl: " + msg);
                try {
                    send(SensorMessage.encodeMessage(msg));
                } catch (SensorMessageException e) {
                    e.printStackTrace();
                }
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
                in.read(bytes);
                buf.put(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buf;
    }
}
