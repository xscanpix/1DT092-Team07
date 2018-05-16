package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessage;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class Sensor {

    protected BlockingQueue<SensorMessage> messages;

    protected int id;
    protected int x;
    protected int y;

    Sensor() {
        this.messages = new ArrayBlockingQueue<>(100);
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

    public SensorMessage getMessage() {
        SensorMessage msg = null;

        try {
            msg = messages.poll(100, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public abstract void start(int msBetweenSend);

    public String toString() {
        return "[Sensor " + id + " { " + x + ", " + "y}] ";
    }
}
