package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessage;
import org.team7.server.sensor.sensormessage.SensorMessageException;
import org.team7.server.sensor.sensormessage.SensorMessageReadings;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.concurrent.TimeUnit;

public class OnlineSensor extends Sensor {

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
    }

    @Override
    public void start(int msBetweenSend) {
        new Thread(() -> {
            while (true) {
                try {
                    int len = in.readByte();

                    byte[] bytes = new byte[len];
                    {
                        int read = in.read(bytes);
                    }
                    ByteBuffer buf = ByteBuffer.allocate(len);
                    buf.put(bytes);

                    SensorMessage msg = SensorMessage.decodeMessage(buf);

                    messages.offer(msg);
                } catch (SensorMessageException | IOException e) {
                    break;
                }
            }
        }).start();
    }
}
