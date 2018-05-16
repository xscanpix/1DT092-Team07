package org.team7.server.sensor;

import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class SensorMessage {

    protected static final int OPCODE_BYTES = 4;
    protected static final int SENSOR_ID_BYTES = 4;

    public enum OPS {
        NOT_USED, SETUP, READINGS
    }

    public static Map<String, Integer> ops = new HashMap<>();

    static {
        ops.put("SETUP", 1);
        ops.put("READINGS", 2);
    }

    private int sensorID;
    private int val1;
    private int val2;

    public SensorMessage(int sensorID, int val1, int val2) {
        this.sensorID = sensorID;
        this.val1 = val1;
        this.val2 = val2;
    }

    public abstract int getOp();

    protected int getSensorID() {
        return sensorID;
    }

    protected int getVal1() {
        return val1;
    }

    protected int getVal2() {
        return val2;
    }

    protected static boolean opIsNotValid(int op) {
        return !ops.containsValue(op);
    }

    public abstract ByteBuffer encodeMessage();

    public static SensorMessage decodeMessage(ByteBuffer buffer) throws SensorMessageException {
        buffer.rewind();

        SensorMessage msg = null;

        int op = buffer.getInt();

        if (opIsNotValid(op)) {
            throw new SensorMessageException("Operation is not valid: " + op);
        }

        if (op == ops.get("SETUP")) {
            msg = new SensorMessageSetup(buffer.getInt(), buffer.getInt(), buffer.getInt());
        } else if (op == ops.get("READINGS")) {
            msg = new SensorMessageReadings(buffer.getInt(), buffer.getInt(), buffer.getInt());
        }

        return msg;
    }
}
