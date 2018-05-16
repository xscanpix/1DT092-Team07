package org.team7.server.robot.robotmessage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling messages from and to robots.
 */
public abstract class RobotMessage {

    /**
     * Variables defining how many bytes the variable should be sent as.
     */
    protected static final int OPCODE_BYTES = 4;
    protected static final int ROBOT_ID_BYTES = 4;

    /**
     * Map for putting the variables in.
     */
    protected Map<String, Object> values;

    /**
     * Static map for which operations exist.
     */
    public static Map<String, Integer> ops = new HashMap<>();

    /**
     * Add the operations here.
     */
    static {
        ops.put("SETUP", 0);
        ops.put("MOVE", 1);
    }

    public RobotMessage() {
        this.values = new HashMap<>();
    }

    public abstract int getOp();

    private static boolean opIsNotValid(int op) {
        return !ops.containsValue(op);
    }

    public abstract ByteBuffer encodeMessage();

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

        if (opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid: " + op);
        }

        if (op == RobotMessage.ops.get("MOVE")) {
            msg = new RobotMessageMove(buffer.getInt(), buffer.getInt());
        }

        return msg;
    }

    public String toString() {
        return "[Robot " + values.get("ID") + " message] Op: " + getOp() + " ";
    }
}
