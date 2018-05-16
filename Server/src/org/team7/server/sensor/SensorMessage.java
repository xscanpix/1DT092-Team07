package org.team7.server.sensor;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class SensorMessage {

    protected static final int OPCODE_BYTES = 4;

    public enum OPS {
        NOT_USED, SETUP, READINGS
    }

    public static Map<String, Integer> ops = new HashMap<>();

    static {
        ops.put("SETUP", 1);
        ops.put("READINGS", 2);
    }

    private Integer reading1;
    private Integer reading2;
    private String data;

    public SensorMessage() {

    }

    public SensorMessage(int reading1, int reading2) {
        this.reading1 = reading1;
        this.reading2 = reading2;
        this.data = null;
    }

    public SensorMessage(String data) {
        this.data = data;
        this.reading1 = null;
        this.reading2 = null;
    }

    protected int getReading1() {
        return reading1;
    }

    protected int getReading2() {
        return reading2;
    }

    protected String getData() {
        return data;
    }

    protected static boolean opIsNotValid(int op) {
        return !ops.containsValue(op);
    }

    public abstract ByteBuffer encodeMessage();

    public static SensorMessage decodeMessage(ByteBuffer buffer) throws SensorMessageException {
        buffer.rewind();

        SensorMessage msg = null;

        int op;
        try {
            op = buffer.getInt();
        } catch (ClassCastException e) {
            throw new SensorMessageException("First byte is not an integer");
        }

        if (opIsNotValid(op)) {
            throw new SensorMessageException("Operation is not valid: " + op);
        }

        if (op == ops.get("SETUP")) {
            byte[] bytes = new byte[buffer.remaining()];
            msg = new SensorMessageSetup(buffer.get(bytes).toString());
        } else if (op == ops.get("READINGS")) {
            msg = new SensorMessageReadings(buffer.getInt(), buffer.getInt());
        }

        return msg;
    }
}
