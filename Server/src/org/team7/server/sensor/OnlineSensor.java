package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessage;
import org.team7.server.sensor.sensormessage.SensorMessageException;
import org.team7.server.sensor.sensormessage.SensorMessageSetup;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;

public class OnlineSensor extends Sensor {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    OnlineSensor(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(ByteBuffer buf) {
        buf.rewind();

        int len = buf.remaining();
        byte[] bytes = new byte[ len ];
        buf.get(bytes);

        try {
            out.writeByte(len);
            out.write(bytes);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void start(int msBetweenSend) {
        new Thread(() -> {
            sendMessage(new SensorMessageSetup(id).encodeMessage());

            while(true) {
                try {
                    int len = in.readByte();

                    byte[] bytes = new byte[ len ];
                    {
                        int read = in.read(bytes);
                    }
                    ByteBuffer buf = ByteBuffer.allocate(len);
                    buf.put(bytes);

                    SensorMessage msg = SensorMessage.decodeMessage(buf);

                    queueFromSensor.offer(msg);

                } catch(SensorMessageException | IOException e) {
                    break;
                }
            }
        }).start();
    }
}
