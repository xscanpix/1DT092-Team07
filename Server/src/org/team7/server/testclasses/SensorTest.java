package org.team7.server.testclasses;

import org.team7.server.sensor.sensormessage.SensorMessage;
import org.team7.server.sensor.sensormessage.SensorMessageReadings;
import org.team7.server.sensor.sensormessage.SensorMessageSetup;

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

            send(new SensorMessageSetup(1, 1, 1).encodeMessage());

            while (socket.isConnected()) {
                SensorMessage msg = new SensorMessageReadings(1, 100, 100);

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
