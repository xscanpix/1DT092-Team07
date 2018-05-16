package org.team7.server.sensor;

import java.nio.ByteBuffer;

public class SensorMessageReadings extends SensorMessage {

    private static final int OPCODE = 2;

    private static final int READING1_BYTES = 4;
    private static final int READING2_BYTES = 4;

    public SensorMessageReadings(int sensorID, int reading1, int reading2) {
        super(sensorID, reading1, reading2);
    }

    @Override
    public int getOp() {
        return OPCODE;
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = 0;

        len += (OPCODE_BYTES + SENSOR_ID_BYTES + READING1_BYTES + READING2_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(OPCODE);
        buf.putInt(getSensorID());
        buf.putInt(getVal1());
        buf.putInt(getVal2());

        return buf;
    }

    public String toString() {
        return "[SensorMessage] Op: " + OPCODE + " ID: " + getSensorID() + " Reading1: " + getVal1() + " Reading2: " + getVal2();
    }
}
