package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessageReadings;

import java.nio.ByteBuffer;
import java.util.Random;

public class OfflineSensor extends Sensor {
    public OfflineSensor(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void sendMessage(ByteBuffer buf) {

    }

    public void start(int msBetweenSend) {
        new Thread(() -> {
            while(true) {
                queueFromSensor.offer(new SensorMessageReadings(getId(), new Random().nextInt(500), new Random().nextInt(500)));

                try {
                    Thread.sleep(msBetweenSend);
                } catch(InterruptedException e) {
                    break;
                }
            }
        }).start();
    }
}
