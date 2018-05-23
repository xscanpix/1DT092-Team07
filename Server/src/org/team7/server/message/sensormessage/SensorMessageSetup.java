package org.team7.server.message.sensormessage;

import org.team7.server.message.Message;

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

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, values.get("ID"), SOURCE_ID_BYTES);

        return buf;
    }
}
