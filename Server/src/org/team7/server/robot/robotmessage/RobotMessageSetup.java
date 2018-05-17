package org.team7.server.robot.robotmessage;

import java.nio.ByteBuffer;

public class RobotMessageSetup extends RobotMessage {

    public RobotMessageSetup(int robotId) {
        values.put("ID", robotId);
    }

    @Override
    public int getOp() {
        return ops.get("SETUP");
    }

    @Override
    public ByteBuffer encodeMessage() {
        int len = RobotMessage.OPCODE_BYTES + ROBOT_ID_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);
        buf.putInt(getOp());
        buf.putInt(values.get("ID"));

        buf.rewind();

        return buf;
    }
}
