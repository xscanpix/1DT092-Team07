package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessageReadings;

public abstract class Sensor {

    protected int id;
    protected int x;
    protected int y;

    Sensor() {
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract boolean isConnected();

    public abstract SensorMessageReadings getReadings();

    public abstract Thread start();

    public boolean equals(Sensor obj) {
        return this.id == obj.id;
    }

    public int hashCode() {
        return Integer.valueOf(this.id).hashCode();
    }
}
