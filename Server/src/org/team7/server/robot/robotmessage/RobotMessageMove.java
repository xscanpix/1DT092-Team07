package org.team7.server.robot.robotmessage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public class RobotMessageMove extends RobotMessage {

    private static final int VALUE_BYTES = 4;

    public static final int FORWARD = 1;
    public static final int BACKWARD = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;

    public RobotMessageMove(int robotID, int direction) throws RobotMessageException {
        values.put("DIR", direction);
        values.put("ID", robotID);
    }

    public int getOp() {
        return RobotMessage.ops.get("MOVE");
    }

    @Override
    public ByteBuffer encodeMessage() {

        int op = getOp();

        int len = RobotMessage.OPCODE_BYTES + ROBOT_ID_BYTES + VALUE_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);
        buf.putInt(getOp());
        buf.putInt((Integer) values.get("ID"));
        buf.putInt((Integer) values.get("DIR"));

        buf.rewind();

        return buf;
    }

    public String toString() {
        return super.toString() + " Dir: " + values.get("DIR");
    }
}
