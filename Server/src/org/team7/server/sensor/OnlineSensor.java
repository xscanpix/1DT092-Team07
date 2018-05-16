package org.team7.server.sensor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class OnlineSensor extends Sensor {
    private BlockingQueue<SensorMessageReadings> readings;

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    OnlineSensor(Socket socket) {
        super();
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.readings = new ArrayBlockingQueue<>(100);
    }

    @Override
    public boolean isConnected() {
        return socket.isConnected();
    }

    @Override
    public SensorMessageReadings getReadings() {

        SensorMessageReadings msg = null;

        try {
            msg = readings.poll(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return msg;
    }

    @Override
    public Thread start() {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    int len = in.readByte();

                    byte[] bytes = new byte[len];
                    in.read(bytes);

                    ByteBuffer buf = ByteBuffer.allocate(len);
                    buf.put(bytes);

                    SensorMessage msg = SensorMessage.decodeMessage(buf);

                    if (msg.getOp() == SensorMessage.ops.get("READINGS")) {
                        readings.offer((SensorMessageReadings) msg);
                    }
                } catch (SensorMessageException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
    }
}
