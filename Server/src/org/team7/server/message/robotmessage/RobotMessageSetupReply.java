package org.team7.server.message.robotmessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public class RobotMessageSetupReply extends RobotMessage {

    private static final int X_BYTES = 4;
    private static final int Y_BYTES = 4;

    public RobotMessageSetupReply(int robotId, int x, int y) {
        values.put("ID", robotId);
        values.put("X", x);
        values.put("Y", y);
    }

    @Override
    public int getOp() {
        return operations.get("SETUPREPLY");
    }

    @Override
    public String getOpName() {
        return "SETUPREPLY";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = RobotMessage.OPCODE_BYTES + SOURCE_ID_BYTES + X_BYTES + Y_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, getValue("ID"), SOURCE_ID_BYTES);
        Message.putBytes(buf, getValue("X"), X_BYTES);
        Message.putBytes(buf, getValue("Y"), Y_BYTES);

        buf.rewind();

        return buf;
    }

    public String toString() {
        return super.toString() + " X: " + values.get("X") + " Y: " + values.get("Y");
    }
}
