package org.team7.server.message.robotmessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public class RobotMessageMove extends RobotMessage {

    public static final int FORWARD = 1;
    public static final int BACKWARD = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;
    public static final int FOLLOWLINE = 5;


    public RobotMessageMove(int robotID, int direction) {
        values.put("ID", robotID);
        values.put("DIR", direction);
    }

    public int getOp() {
        return operations.get("MOVE");
    }

    public String getOpName() {
        return "MOVE";
    }

    public ByteBuffer encodeMessage() {
        int len = OPCODE_BYTES + SOURCE_ID_BYTES + DIRECTION_BYTES;

        ByteBuffer buf = ByteBuffer.allocate(len);

        Message.putBytes(buf, getOp(), OPCODE_BYTES);
        Message.putBytes(buf, getValue("ID"), SOURCE_ID_BYTES);
        Message.putBytes(buf, getValue("DIR"), DIRECTION_BYTES);

        return buf;
    }

    public String toString() {
        return super.toString() + " Dir: " + values.get("DIR");
    }
}
