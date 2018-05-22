package org.team7.server.message.sensormessage;

import org.team7.server.message.MessageException;

public class SensorMessageException extends MessageException {
    SensorMessageException(String message) {
        super("[SensorMessageException] " + message);
    }
}
