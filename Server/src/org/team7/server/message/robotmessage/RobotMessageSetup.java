package org.team7.server.message.robotmessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public class RobotMessageSetup extends RobotMessage {

    public RobotMessageSetup(int robotId) {
        values.put("ID", robotId);
    }

    @Override
    public int getOp() {
        return operations.get("SETUP");
    }

    @Override
    public String getOpName() {
        return "SETUP";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = RobotMessage.OPCODE_BYTES + SOURCE_ID_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, getValue("ID"), SOURCE_ID_BYTES);

        return buf;
    }
}
