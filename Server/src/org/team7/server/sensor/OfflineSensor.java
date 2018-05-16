package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessageReadings;

public class OfflineSensor extends Sensor {
    OfflineSensor(int id, int x, int y) {
        super();
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public SensorMessageReadings getReadings() {
        return new SensorMessageReadings(getId(), 100, 100);
    }

    @Override
    public Thread start() {
        return null;
    }
}
