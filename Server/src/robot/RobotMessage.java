package robot;

import java.nio.ByteBuffer;

/**
 * Class for handling messages from and to robots.
 */
public class RobotMessage {

    /**
     * THe number of bytes of the opcode.
     */
    private static final int OPCODE_BYTES = 4;

    /**
     * Enum of the supported operations. The first one is not used to offset by one.
     */
    public enum OPS {
        NOT_USED, TEST, OP_2
    }

    private int op;
    private String data;

    /**
     * Creates a new message.
     *
     * @param op   the operation
     * @param data the data
     */
    RobotMessage(int op, String data) {
        this.op = op;
        this.data = data;
    }

    /**
     * Checks if the operation number is valid.
     *
     * @param op the operation
     * @return false if it exists, true otherwise.
     */
    private static boolean opIsNotValid(int op) {
        try {
            OPS res = OPS.values()[op];
        } catch (ArrayIndexOutOfBoundsException e) {
            return true;
        }

        return false;
    }

    /**
     * Encodes a message to a buffer of bytes.
     *
     * @param message The message to encode.
     * @return The buffered bytes
     * @throws RobotMessageException .
     */
    static ByteBuffer encodeMessage(RobotMessage message) throws RobotMessageException {
        if (opIsNotValid(message.getOp())) {
            throw new RobotMessageException("Operation is not valid");
        }

        int len = 0;

        int dataLen = message.getData().getBytes().length;

        len += (OPCODE_BYTES + dataLen);

        ByteBuffer buf = ByteBuffer.allocate(len);

        buf.putInt(message.getOp());
        buf.put(message.getData().getBytes());

        return buf;
    }

    /**
     * Decodes a buffer of bytes.
     *
     * @param message The bytes to deoode
     * @return A new decodes message.
     * @throws RobotMessageException .
     */
    static RobotMessage decodeMessage(ByteBuffer message) throws RobotMessageException {
        message.rewind();

        int op;
        String data;

        try {
            op = message.getInt();
        } catch (ClassCastException e) {
            throw new RobotMessageException("First byte is not an integer");
        }

        if (opIsNotValid(op)) {
            throw new RobotMessageException("Operation is not valid: " + op);
        }

        byte[] remaining = new byte[message.remaining()];
        message.get(remaining);

        data = new String(remaining);

        return new RobotMessage(op, data);
    }

    private int getOp() {
        return op;
    }

    private String getData() {
        return data;
    }

    public String toString() {
        return "[RobotMessage] Op: " + op + " Data: " + data;
    }
}
