package robot;

public class RobotMessage {

    // OP|DATA
    private enum OPS {
        OP_1, OP_2
    }

    private byte[] byteMessage;

    private Integer op;

    public RobotMessage(byte[] byteMessage) {
        if (byteMessage != null) {
            this.byteMessage = byteMessage;
        } else {
            throw new NullPointerException();
        }
        this.op = null;
    }

    private int getOp() {
        return op;
    }

    public RobotMessage parseMessage() throws RobotMessageException {

        try {
            op = (int) (byteMessage[0]);
        } catch (ClassCastException e) {
            throw new RobotMessageException("First byte is not an integer");
        }

        try {
            OPS res = OPS.values()[op];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RobotMessageException("Operation does not exist");
        }

        return this;
    }


}
