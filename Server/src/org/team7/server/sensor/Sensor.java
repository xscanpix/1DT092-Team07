package org.team7.server.sensor;

import java.net.Socket;

public class Sensor {

    private int x;
    private int y;
    private boolean online;
    private Socket socket;

    Sensor(int x, int y, boolean online, Socket socket) {
        this.x = x;
        this.y = y;
        this.online = online;
        this.socket = socket;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public float getReadings() {
        return 0;
    }
}
