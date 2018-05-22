package org.team7.server.message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class Message {

    public static final int OPCODE_BYTES = 4;
    public static final int SOURCE_ID_BYTES = 4;
    public static Map<String, Integer> operations = new HashMap<>();

    public Map<String, Integer> values;

    public Message() {
        this.values = new HashMap<>();
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
