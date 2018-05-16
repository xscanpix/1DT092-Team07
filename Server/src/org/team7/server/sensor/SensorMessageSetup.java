package org.team7.server.sensor;

import java.nio.ByteBuffer;

public class SensorMessageSetup extends SensorMessage {

    private static int OPCODE = 1;

    public SensorMessageSetup(String data) {
        super(data);
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = 0;

        len += (OPCODE_BYTES + getData().getBytes().length);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(OPCODE);
        buf.put(getData().getBytes());

        return buf;
    }

    public String toString() {
        return "[SensorMessage] Op: " + OPCODE + " Data: " + getData();
    }
}
