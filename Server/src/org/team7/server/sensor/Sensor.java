package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessage;

import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public abstract class Sensor {
    private static int SENSORID = 0;
    protected BlockingQueue<SensorMessage> queueFromSensor;
    protected int id;
    protected int x;
    protected int y;

    Sensor() {
        id = SENSORID++;
        queueFromSensor = new ArrayBlockingQueue<>(50);
    }

    public int getId() {
        return id;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public SensorMessage pollMessage() {
        SensorMessage msg = null;

        try {
            if(!queueFromSensor.isEmpty()) {
                msg = queueFromSensor.poll(100, TimeUnit.MILLISECONDS);
            }
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        return msg;
    }

    public abstract void sendMessage(ByteBuffer buf);

    public abstract void start(int msBetweenSend);

    public String toString() {
        return "[Sensor " + id + "] ";
    }
}
