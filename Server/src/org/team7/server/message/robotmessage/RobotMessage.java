package org.team7.server.message.robotmessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

/**
 * Class for handling queueFromSensor from and to robots.
 */
public abstract class RobotMessage extends Message {
    static {
        operations.put("SETUP", 0);
        operations.put("SETUPREPLY", 1);
        operations.put("MOVE", 2);
        operations.put("MOVEREPLY", 3);
    }

    public RobotMessage() {

    }

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

        if(op == RobotMessage.operations.get("SETUP")) {
            msg = new RobotMessageSetup(buffer.getInt());
        } else if(op == RobotMessage.operations.get("SETUPREPLY")) {
            msg = new RobotMessageSetupReply(buffer.getInt(), buffer.getInt(), buffer.getInt());
        } else if(op == RobotMessage.operations.get("MOVE")) {
            msg = new RobotMessageMove(buffer.getInt(), buffer.getInt());
        }


        return msg;
    }
    
    public String toString() {
        return "[Robot message] ID: " + values.get("ID") + " Op: " + getOp() + " ";
    }
}
