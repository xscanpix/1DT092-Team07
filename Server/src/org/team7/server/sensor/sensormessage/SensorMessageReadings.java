package org.team7.server.sensor.sensormessage;

import java.nio.ByteBuffer;

public class SensorMessageReadings extends SensorMessage {

    private static final int OPCODE = 2;

    private static final int READING1_BYTES = 4;
    private static final int READING2_BYTES = 4;

    public SensorMessageReadings(int sensorID, int reading1, int reading2) {
        super();
        values.put("ID", sensorID);
        values.put("R1", reading1);
        values.put("R2", reading2);
    }

    public int getOp() {
        return OPCODE;
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = 0;

        len += (OPCODE_BYTES + SENSOR_ID_BYTES + READING1_BYTES + READING2_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(OPCODE);
        buf.putInt((Integer) values.get("ID"));
        buf.putInt((Integer) values.get("R1"));
        buf.putInt((Integer) values.get("R2"));

        return buf;
    }

    public String toString() {
        return super.toString() + "Op: " + OPCODE + " Reading1: " + values.get("R1") + " Reading2: " + values.get("R2");
    }
}
