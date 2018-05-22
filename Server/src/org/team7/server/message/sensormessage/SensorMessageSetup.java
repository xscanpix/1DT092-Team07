package org.team7.server.message.sensormessage;

import java.nio.ByteBuffer;

public class SensorMessageSetup extends SensorMessage {

    public SensorMessageSetup(int sensorID) {
        values.put("ID", sensorID);
    }

    public int getOp() {
        return operations.get("SETUP");
    }

    @Override
    public String getOpName() {
        return "SETUP";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = (OPCODE_BYTES + SOURCE_ID_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(getOp());
        buf.putInt(values.get("ID"));

        return buf;
    }
}
