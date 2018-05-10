package robot;

import java.nio.ByteBuffer;

public class RobotMessage {

    private static final int OPCODE_BYTES = 4;

    public enum OPS {
        NOT_USED, TEST, OP_2
    }

    private int op;
    private String data;

    RobotMessage(int op, String data) {
        this.op = op;
        this.data = data;
    }

    private int getOp() {
        return op;
    }

    private String getData() {
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

    static ByteBuffer encodeMessage(RobotMessage msg) throws RobotMessageException {
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

    static RobotMessage decodeMessage(ByteBuffer message) throws RobotMessageException {

        message.rewind();

        int op;
        String data;

        try {
            op = message.getInt();
        } catch (ClassCastException e) {
            throw new RobotMessageException("[DecodeMessage] First byte is not an integer");
        }

        if (opIsNotValid(op)) {
            throw new RobotMessageException("[DecodeMessage] Operation is not valid: " + op);
        }

        byte[] remaining = new byte[message.remaining()];
        message.get(remaining);

        data = new String(remaining);

        return new RobotMessage(op, data);
    }

    public String toString() {
        return "[RobotMessage] Op: " + op + " Data: " + data;
    }
}
