package robot;

public class RobotMessageException extends Exception {
    public RobotMessageException(String message) {
        super("[RobotMessageException] " + message);
    }
}
