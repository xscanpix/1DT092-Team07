package org.team7.server.sensor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Sensor {

    private int x;
    private int y;
    private boolean online;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;

    Sensor(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Sensor(int x, int y, boolean online, Socket socket) {
        this.x = x;
        this.y = y;
        this.online = online;
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Sensor(int x, int y) {
        new Sensor(x, y, false, null);
    }

    boolean isConnected() {
        return socket.isConnected();
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

    public DataInputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }
}
