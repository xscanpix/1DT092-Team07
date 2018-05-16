package org.team7.server.sensor;

import org.team7.server.sensor.sensormessage.SensorMessageReadings;
import org.team7.server.sensor.sensormessage.SensorMessageSetup;

import java.util.Random;

public class OfflineSensor extends Sensor {
    public OfflineSensor(int id, int x, int y) {
        super();
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public void start(int msBetweenSend) {
        new Thread(() -> {
            messages.offer(new SensorMessageSetup(id, x, y));

            while (true) {
                messages.offer(new SensorMessageReadings(getId(), new Random().nextInt(), new Random().nextInt()));

                try {
                    Thread.sleep(msBetweenSend);
                } catch (InterruptedException e) {
                    break;
                }
            }
        }).start();
    }
}
