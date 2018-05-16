package org.team7.server.sensor;

import java.nio.ByteBuffer;

public class SensorMessageReadings extends SensorMessage {

    private static final int READING1_BYTES = 4;
    private static final int READING2_BYTES = 4;
    private static int OPCODE = 2;

    public SensorMessageReadings(int reading1, int reading2) {
        super(reading1, reading2);
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = 0;

        len += (OPCODE_BYTES + READING1_BYTES + READING2_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(OPCODE);
        buf.putInt(getReading1());
        buf.putInt(getReading2());

        return buf;
    }

    public String toString() {
        return "[SensorMessage] Op: " + OPCODE + " Reading1: " + getReading1() + " Reading2: " + getReading2();
    }
}
