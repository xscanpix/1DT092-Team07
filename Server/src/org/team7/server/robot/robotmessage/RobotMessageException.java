package org.team7.server.robot.robotmessage;

public class RobotMessageException extends Exception {
    RobotMessageException(String message) {
        super("[RobotMessageException] " + message);
    }
}
