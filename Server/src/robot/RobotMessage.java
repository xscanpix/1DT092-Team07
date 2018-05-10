package robot;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class RobotMessage {

    private static final int OPCODE_BYTES = 4;

    // OP   |DATA
    // INT|STRING
    public enum OPS {
        NOT_USED, TEST, OP_2
    }

    private int op;
    private String data;

    public RobotMessage(int op, String data) {
        this.op = op;
        this.data = data;
    }

    public int getOp() {
        return op;
    }

    public String getData() {
        return data;
    }

    private static boolean opIsNotValid(int op) {
        try {
            OPS res = OPS.values()[op];
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    public static ByteBuffer encodeMessage(RobotMessage msg) throws RobotMessageException {
        if (opIsNotValid(msg.getOp())) {
            throw new RobotMessageException("Operation is not valid");
        }

        int len = 0;

        int dataLen = msg.getData().getBytes().length;

        len += (OPCODE_BYTES + dataLen);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(msg.getOp());
        buf.put(msg.getData().getBytes());

        return buf;
    }

    public static RobotMessage decodeMessage(ByteBuffer message) throws RobotMessageException {

        message.rewind();

        int op;
        String data;

        try {
            op = message.getInt();
        } catch (ClassCastException e) {
            throw new RobotMessageException("First byte is not an integer");
        }

        if (opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid");
        }

        byte[] remaining = new byte[message.remaining()];
        message.get(remaining);

        data = new String(remaining);

        return new RobotMessage(op, data);
    }

    public String toString() {
        return "Op: " + op + " Data: " + data;
    }
}
