package org.team7.server.server;

import org.team7.server.robot.RobotControl;
import org.team7.server.robot.RobotMessage;
import org.team7.server.sensor.SensorControl;
import org.team7.server.sensor.SensorMessage;
import org.team7.server.sensor.SensorMessageReadings;

import java.util.List;

/*
 * Main entry point for the server.
 **/
public class Server {
    private static RobotControl robotControl;
    private static SensorControl sensorControl;
    private static ServerControl serverControl;

    private static boolean alive;

    public Server() {

    }

    /**
     * Initializes and starts the controls.
     */
    public void initialize() {
        serverControl = new ServerControl(this);
        robotControl = new RobotControl(5555);
        sensorControl = new SensorControl(5556);

        robotControl.initialize();
        sensorControl.initialize();

        serverControl.start();
        robotControl.start(100);
        sensorControl.start();
    }

    public Thread start() {
        alive = true;

        /*
         * Test thread that polls the queue in org.team7.server.robot control for messages from robots.
         * */
        Thread thread = new Thread(() -> {
            while (alive) {
                try {
                    List<RobotMessage> rmessages = robotControl.pollMessages();
                    List<SensorMessage> smessages = sensorControl.pollMessages();

                    for (RobotMessage rmessage : rmessages) {
                        if (rmessage != null) {
                            System.out.println("[RobotControl] Message from Robot: " + rmessage);
                        }
                    }

                    for (SensorMessage smessage : smessages) {
                        if (smessage != null) {
                            System.out.println("[SensorControl] Message from Sensor: " + smessage);
                        }
                    }

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
    }

    void stop() {
        alive = false;
    }

    boolean isAlive() {
        return alive;
    }
}
