package org.team7.server.message.robotmessage;

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
        buf.putInt(getOp());
        buf.putInt((Integer) values.get("ID"));
        buf.putInt((Integer) values.get("REPLY"));

        buf.rewind();

        return buf;
    }

    public String toString() {
        return super.toString() + " Reply: " + values.get("REPLY");
    }
}
