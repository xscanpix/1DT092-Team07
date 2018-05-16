package org.team7.server.sensor.sensormessage;

import java.nio.ByteBuffer;

public class SensorMessageSetup extends SensorMessage {

    private static final int OPCODE = 1;

    private static final int X_BYTES = 4;
    private static final int Y_BYTES = 4;

    public SensorMessageSetup(int sensorID, int x, int y) {
        super();
        values.put("ID", sensorID);
        values.put("X", x);
        values.put("Y", y);
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
        buf.putInt((Integer) values.get("X"));
        buf.putInt((Integer) values.get("Y"));

        return buf;
    }

    public String toString() {
        return "[SensorMessage] Op: " + OPCODE + " ID: " + values.get("ID") + " X: " + values.get("X") + " Y: " + values.get("Y");
    }
}
