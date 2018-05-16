package org.team7.server.robot.robotmessage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for handling messages from and to robots.
 */
public abstract class RobotMessage {

    /**
     * THe number of bytes of the opcode.
     */
    private static final int OPCODE_BYTES = 4;
    private static final int SENSOR_ID_BYTES = 4;

    private Map<String, Object> values;

    private static Map<String, Integer> ops = new HashMap<>();

    static {
    }

    public RobotMessage() {
        this.values = new HashMap<>();
    }

    private static boolean opIsNotValid(int op) {
        return !ops.containsValue(op);
    }

    public abstract ByteBuffer encodeMessage(RobotMessage message) throws RobotMessageException;

    public static RobotMessage decodeMessage(ByteBuffer buffer) throws RobotMessageException {
        buffer.rewind();

        RobotMessage msg = null;

        int op = buffer.getInt();

        if (opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid: " + op);
        }


        return msg;
    }
}
