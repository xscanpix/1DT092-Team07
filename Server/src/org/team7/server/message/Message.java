package org.team7.server.message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class Message {

    public static final int OPCODE_BYTES = 2;
    public static final int SOURCE_ID_BYTES = 2;
    public static final int X_BYTES = 2;
    public static final int Y_BYTES = 2;
    public static final int DIRECTION_BYTES = 2;
    public static final int READING1_BYTES = 2;
    public static final int READING2_BYTES = 2;
    public static final int REPLY_BYTES = 2;

    public static Map<String, Integer> operations = new HashMap<>();

    public Map<String, Integer> values;

    public Message() {
        this.values = new HashMap<>();
    }

    public static void putBytes(ByteBuffer buf, int value, int numBytes) {
        if(numBytes == 2) {
            buf.putShort((short) value);
        } else if(numBytes == 4) {
            buf.putInt(value);
        }
    }

    public static int getInt(ByteBuffer buf, int numBytes) {

        int result = 0;

        if(numBytes == 2) {
            result = buf.getShort();
        } else if(numBytes == 4) {
            result = buf.getInt();
        }

        return result;
    }

    public abstract int getOp();

    public abstract String getOpName();

    public abstract ByteBuffer encodeMessage();

    public static boolean opIsNotValid(int op) {
        return !operations.containsValue(op);
    }

    public int getValue(String identifier) {
        return values.get(identifier);
    }
}
