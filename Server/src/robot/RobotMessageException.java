package robot;

public class RobotMessageException extends Exception {
    RobotMessageException(String message) {
        super("[RobotMessageException] " + message);
    }
}
