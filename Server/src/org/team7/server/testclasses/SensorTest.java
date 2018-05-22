package org.team7.server.testclasses;

import org.team7.server.message.sensormessage.SensorMessage;
import org.team7.server.message.sensormessage.SensorMessageException;
import org.team7.server.message.sensormessage.SensorMessageReadings;
import org.team7.server.message.sensormessage.SensorMessageSetupReply;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Random;

/**
 * A test class for simulating a sensor.
 */
public class SensorTest {

    private STATES state = STATES.SETUP;
    private int id;

    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    private int x;
    private int y;

    public SensorTest(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int msBetweenSend) {
        new Thread(() -> {

            while(true) {
                if(state == STATES.SETUP) {
                    ByteBuffer buf = receive();

                    try {
                        SensorMessage msg = SensorMessage.decodeMessage(buf);
                        if(msg.getOp() == SensorMessage.operations.get("SETUP")) {
                            id = msg.values.get("ID");
                            send(new SensorMessageSetupReply(msg.values.get("ID"), x, y).encodeMessage());
                            state = STATES.READY;
                        }
                    } catch(SensorMessageException e) {
                        e.printStackTrace();
                    }
                } else if(state == STATES.READY) {
                    send(new SensorMessageReadings(id, new Random().nextInt(500), new Random().nextInt(500)).encodeMessage());
                }

                try {
                    Thread.sleep(msBetweenSend);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void send(ByteBuffer message) {
        try {
            out.writeByte(message.array().length);
            out.write(message.array());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private ByteBuffer receive() {
        ByteBuffer buf = null;
        try {
            int len = in.readByte();
            buf = ByteBuffer.allocate(len);
            byte[] bytes = new byte[ len ];
            {
                int read = in.read(bytes);
            }
            buf.put(bytes);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    private enum STATES {SETUP, READY}
}
