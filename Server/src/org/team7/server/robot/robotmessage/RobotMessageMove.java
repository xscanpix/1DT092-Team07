package org.team7.server.robot.robotmessage;

import java.nio.ByteBuffer;

public class RobotMessageMove extends RobotMessage {
    private static final int DIRECTION_BYTES = 4;

    public static final int FORWARD = 1;
    public static final int BACKWARD = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;

    public RobotMessageMove(int robotID, int direction) {
        values.put("ID", robotID);
        values.put("DIR", direction);
    }

    public int getOp() {
        return ops.get("MOVE");
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = OPCODE_BYTES + ROBOT_ID_BYTES + DIRECTION_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);
        buf.putInt(getOp());
        buf.putInt(values.get("ID"));
        buf.putInt(values.get("DIR"));

        buf.rewind();

        return buf;
    }

    public String toString() {
        return super.toString() + " Dir: " + values.get("DIR");
    }
}
