package org.team7.server.sensor;

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
        if (this.id == obj.id) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return Integer.valueOf(this.id).hashCode();
    }
}
