package robot;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class RobotMessage {

    private static int OPCODE_BYTES = 2;

    // OP|DATA
    private enum OPS {
        OP_1, OP_2
    }

    private short op;
    private String data;

    private RobotMessage(short op, String data) {
        this.op = op;
        this.data = data;
    }

    private short getOp() {
        return op;
    }

    private String getData() {
        return data;
    }

    private static boolean opIsNotValid(short op) {
        try {
            OPS res = OPS.values()[op];
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    public static byte[] encodeMessage(short op, String data) throws RobotMessageException {
        if(opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid");
        }

        if(data == null) {
            data = "";
        }

        int len = 0;

        int opLen = OPCODE_BYTES;
        int dataLen = data.getBytes().length;

        len += (opLen + dataLen);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putShort(op);
        buf.put(data.getBytes());

        return buf.array();
    }

    public static RobotMessage decodeMessage(byte[] message) throws RobotMessageException {

        ByteBuffer buf = ByteBuffer.wrap(message);

        short op;
        String data;

        try {
            op = buf.getShort();
        } catch (ClassCastException e) {
            throw new RobotMessageException("First byte is not an integer");
        }

        if(opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid");
        }

        byte[] remaining = new byte[buf.remaining()];
        buf.get(remaining);

        data = new String(remaining, StandardCharsets.UTF_8);

        return new RobotMessage(op, data);
    }

    public String toString() {
        return op + data;
    }
}
