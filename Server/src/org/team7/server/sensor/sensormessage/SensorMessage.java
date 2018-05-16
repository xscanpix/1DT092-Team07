package org.team7.server.sensor.sensormessage;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

public abstract class SensorMessage {

    static final int OPCODE_BYTES = 4;
    static final int SENSOR_ID_BYTES = 4;

    Map<String, Object> values;

    public static Map<String, Integer> ops = new HashMap<>();

    static {
        ops.put("SETUP", 1);
        ops.put("READINGS", 2);
    }

    public SensorMessage() {
        this.values = new HashMap<>();
    }

    public abstract int getOp();

    private static boolean opIsNotValid(int op) {
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

    public String toString() {
        return "[Sensor " + values.get("ID") + " message] Op: " + getOp() + " ";
    }
}
