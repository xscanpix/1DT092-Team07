package org.team7.server.robot.robotmessage;

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
        return RobotMessage.ops.get("SETUPREPLY");
    }

    @Override
    public ByteBuffer encodeMessage() {
        int op = getOp();

        int len = RobotMessage.OPCODE_BYTES + ROBOT_ID_BYTES + X_BYTES + Y_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);
        buf.putInt(getOp());
        buf.putInt((Integer) values.get("ID"));
        buf.putInt((Integer) values.get("X"));
        buf.putInt((Integer) values.get("Y"));

        buf.rewind();

        return buf;
    }

    public String toString() {
        return super.toString() + " X: " + values.get("X") + " Y: " + values.get("Y");
    }
}
