package org.team7.server.message.sensormessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public class SensorMessageReadings extends SensorMessage {



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

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, getValue("ID"), SOURCE_ID_BYTES);
        Message.putBytes(buf, getValue("R1"), READING1_BYTES);
        Message.putBytes(buf, getValue("R2"), READING2_BYTES);

        return buf;
    }

    public String toString() {
        return super.toString() + "Op: " + getOp() + " Reading1: " + values.get("R1") + " Reading2: " + values.get("R2");
    }
}
