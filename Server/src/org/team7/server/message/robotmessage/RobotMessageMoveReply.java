package org.team7.server.message.robotmessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public class RobotMessageMoveReply extends RobotMessage {

    public static final int OKAY = 0;
    public static final int NOT_OKAY = 1;
    private static final int REPLY_BYTES = 4;

    public RobotMessageMoveReply(int robotId, int reply) {
        values.put("ID", robotId);
        values.put("REPLY", reply);
    }

    @Override
    public int getOp() {
        return operations.get("MOVEREPLY");
    }

    @Override
    public String getOpName() {
        return "MOVEREPLY";
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = RobotMessage.OPCODE_BYTES + SOURCE_ID_BYTES + REPLY_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, getValue("ID"), SOURCE_ID_BYTES);
        Message.putBytes(buf, getValue("REPLY"), REPLY_BYTES);

        buf.rewind();

        return buf;
    }

    public String toString() {
        return super.toString() + " Reply: " + values.get("REPLY");
    }
}
