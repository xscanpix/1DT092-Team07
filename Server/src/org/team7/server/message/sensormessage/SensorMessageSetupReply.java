package org.team7.server.message.sensormessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public class SensorMessageSetupReply extends SensorMessage {

    public SensorMessageSetupReply(int sensorId, int x, int y) {
        values.put("ID", sensorId);
        values.put("X", x);
        values.put("Y", y);
    }

    public int getOp() {
        return SensorMessage.operations.get("SETUPREPLY");
    }

    @Override
    public String getOpName() {
        return "SETUPREPLY";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = (OPCODE_BYTES + SOURCE_ID_BYTES + X_BYTES + Y_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, getValue("ID"), SOURCE_ID_BYTES);
        Message.putBytes(buf, getValue("X"), X_BYTES);
        Message.putBytes(buf, getValue("Y"), Y_BYTES);

        return buf;
    }
}
