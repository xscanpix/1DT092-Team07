package org.team7.server.message.sensormessage;

import java.nio.ByteBuffer;

public class SensorMessageReadings extends SensorMessage {

    private static final int READING1_BYTES = 4;
    private static final int READING2_BYTES = 4;

    public SensorMessageReadings(int sensorID, int reading1, int reading2) {
        values.put("ID", sensorID);
        values.put("R1", reading1);
        values.put("R2", reading2);
    }

    public int getOp() {
        return operations.get("READINGS");
    }

    @Override
    public String getOpName() {
        return "READINGS";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = (OPCODE_BYTES + SOURCE_ID_BYTES + READING1_BYTES + READING2_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(getOp());
        buf.putInt(values.get("ID"));
        buf.putInt(values.get("R1"));
        buf.putInt(values.get("R2"));

        return buf;
    }

    public String toString() {
        return super.toString() + "Op: " + getOp() + " Reading1: " + values.get("R1") + " Reading2: " + values.get("R2");
    }
}
