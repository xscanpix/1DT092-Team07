package sensor;

class SensorMessageException extends Exception {
    SensorMessageException(String message) {
        super("[SensorMessageException] " + message);
    }
}
