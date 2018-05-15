package org.team7.server.sensor;

import java.nio.ByteBuffer;

public class SensorMessage {

    private static final int OPCODE_BYTES = 4;
    private static final int READING1_BYTES = 4;
    private static final int READING2_BYTES = 4;

    public enum OPS {
        NOT_USED, READINGS
    }

    private int op;
    private int reading1;
    private int reading2;

    public SensorMessage(int op, int reading1, int reading2) {
        this.op = op;
        this.reading1 = reading1;
        this.reading2 = reading2;
    }

    private int getOp() {
        return op;
    }

    private int getReading1() {
        return reading1;
    }

    private int getReading2() {
        return reading2;
    }

    private static boolean opIsNotValid(int op) {
        try {
            OPS res = OPS.values()[op];
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    static ByteBuffer encodeMessage(SensorMessage msg) throws SensorMessageException {
        if (opIsNotValid(msg.getOp())) {
            throw new SensorMessageException("Operation is not valid");
        }

        int len = 0;

        len += (OPCODE_BYTES + READING1_BYTES + READING2_BYTES);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(msg.getOp());
        buf.putInt(msg.getReading1());
        buf.putInt(msg.getReading2());

        return buf;
    }

    static SensorMessage decodeMessage(ByteBuffer message) throws SensorMessageException {

        message.rewind();

        int op;
        int reading1;
        int reading2;

        try {
            op = message.getInt();
        } catch (ClassCastException e) {
            throw new SensorMessageException("First byte is not an integer");
        }

        if (opIsNotValid(op)) {
            throw new SensorMessageException("Operation is not valid: " + op);
        }

        reading1 = message.getInt();
        reading2 = message.getInt();

        return new SensorMessage(op, reading1, reading2);
    }

    public String toString() {
        return "[SensorMessage] Op: " + op + " Reading1: " + getReading1() + " Reading2: " + getReading2();
    }
}
