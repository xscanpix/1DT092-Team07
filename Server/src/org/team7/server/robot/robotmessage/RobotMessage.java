package org.team7.server.robot.robotmessage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling queueFromSensor from and to robots.
 */
public abstract class RobotMessage {

    /**
     * Variables defining how many bytes the variable should be sent as.
     */
    static final int OPCODE_BYTES = 4;
    static final int ROBOT_ID_BYTES = 4;

    static {
        ops.put("SETUP", 0);
        ops.put("SETUPREPLY", 1);
        ops.put("MOVE", 2);
        ops.put("MOVEREPLY", 3);
    }

    /**
     * Static map for which operations exist.
     */
    public static Map<String, Integer> ops = new HashMap<>();

    /**
     * Map for putting the variables in.
     */
    public Map<String, Integer> values;

    public RobotMessage() {
        this.values = new HashMap<>();
    }

    public abstract int getOp();

    /**
     * Decodes the buffer of bytes and creates the correct RobotMessage object.
     *
     * @param buffer the buffer of bytes
     * @return the robot message
     * @throws RobotMessageException .
     */
    public static RobotMessage decodeMessage(ByteBuffer buffer) throws RobotMessageException {
        buffer.rewind();

        RobotMessage msg = null;

        int op = buffer.getInt();

        if(opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid: " + op);
        }

        if(op == RobotMessage.ops.get("SETUP")) {
            msg = new RobotMessageSetup(buffer.getInt());
        } else if(op == RobotMessage.ops.get("SETUPREPLY")) {
            msg = new RobotMessageSetupReply(buffer.getInt(), buffer.getInt(), buffer.getInt());
        } else if(op == RobotMessage.ops.get("MOVE")) {
            msg = new RobotMessageMove(buffer.getInt(), buffer.getInt());
        }


        return msg;
    }

    private static boolean opIsNotValid(int op) {
        return !ops.containsValue(op);
    }

    public abstract ByteBuffer encodeMessage();

    public int getValue(String identifier) {
        return values.get(identifier);
    }

    public String toString() {
        return "[Robot message] ID: " + values.get("ID") + " Op: " + getOp() + " ";
    }
}
