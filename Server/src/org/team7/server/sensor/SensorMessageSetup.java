package org.team7.server.sensor;

import java.nio.ByteBuffer;

public class SensorMessageSetup extends SensorMessage {

    private static final int OPCODE = 1;

    private static final int X_BYTES = 4;
    private static final int Y_BYTES = 4;

    public SensorMessageSetup(int sensorID, int x, int y) {
        super(sensorID, x, y);
    }

    @Override
    public int getOp() {
        return OPCODE;
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = 0;

        len += (OPCODE_BYTES + SENSOR_ID_BYTES + X_BYTES + Y_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(OPCODE);
        buf.putInt(getVal1());
        buf.putInt(getVal2());

        return buf;
    }

    public String toString() {
        return "[SensorMessage] Op: " + OPCODE + " ID: " + getSensorID() + " X: " + getVal1() + " Y: " + getVal2();
    }
}
