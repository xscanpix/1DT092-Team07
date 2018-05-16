package org.team7.server.sensor;

public class SensorMessageException extends Exception {
    SensorMessageException(String message) {
        super("[SensorMessageException] " + message);
    }
}
