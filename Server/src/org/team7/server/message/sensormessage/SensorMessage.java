package org.team7.server.message.sensormessage;

import org.team7.server.message.Message;

import java.nio.ByteBuffer;

public abstract class SensorMessage extends Message {
    static {
        operations.put("SETUP", 0);
        operations.put("SETUPREPLY", 1);
        operations.put("READINGS", 2);
    }

    public SensorMessage() {

    }

    public static SensorMessage decodeMessage(ByteBuffer buffer) throws SensorMessageException {
        buffer.rewind();

        SensorMessage msg = null;

        int op = Message.getInt(buffer, OPCODE_BYTES);

        if(opIsNotValid(op)) {
            throw new SensorMessageException("Operation is not valid: " + op);
        }

        if(op == operations.get("SETUP")) {
            msg = new SensorMessageSetup(Message.getInt(buffer, SOURCE_ID_BYTES));
        } else if(op == operations.get("SETUPREPLY")) {
            msg = new SensorMessageSetupReply(Message.getInt(buffer, SOURCE_ID_BYTES), Message.getInt(buffer, X_BYTES), Message.getInt(buffer, Y_BYTES));
        } else if(op == operations.get("READINGS")) {
            msg = new SensorMessageReadings(Message.getInt(buffer, SOURCE_ID_BYTES), Message.getInt(buffer, READING1_BYTES), Message.getInt(buffer, READING2_BYTES));
        }

        return msg;
    }

    public String toString() {
        return "[Sensor message] ID: " + values.get("ID") + " Op: " + getOpName() + " ";
    }
}
