package org.team7.server.sensor;

class SensorMessageException extends Exception {
    SensorMessageException(String message) {
        super("[SensorMessageException] " + message);
    }
}
