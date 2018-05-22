package org.team7.server.sensor.sensormessage;

public class SensorMessageException extends Exception {
    SensorMessageException(String message) {
        super("[SensorMessageException] " + message);
    }
}
