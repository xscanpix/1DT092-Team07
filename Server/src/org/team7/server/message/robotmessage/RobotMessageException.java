package org.team7.server.message.robotmessage;

import org.team7.server.message.MessageException;

public class RobotMessageException extends MessageException {
    RobotMessageException(String message) {
        super("[RobotMessageException] " + message);
    }
}
