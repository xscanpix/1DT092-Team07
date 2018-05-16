package org.team7.server.server;

import org.team7.server.robot.Robot;
import org.team7.server.robot.RobotControl;
import org.team7.server.robot.robotmessage.RobotMessage;
import org.team7.server.robot.robotmessage.RobotMessageSetup;
import org.team7.server.sensor.Sensor;
import org.team7.server.sensor.SensorControl;
import org.team7.server.sensor.sensormessage.SensorMessage;

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

        robotControl.start();
        sensorControl.start();
    }

    public Sensor createOfflineSensor(int x, int y) {
        return sensorControl.createOfflineSensor(x, y);
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
                            serverControl.setText("[RobotControl] Message from Robot: " + rmessage);
                            if (rmessage.getOp() == RobotMessage.ops.get("SETUPREPLY")) {
                                Robot robot = robotControl.getRobot((Integer) rmessage.values.get("ID"));
                                robot.setPos((Integer) rmessage.values.get("X"), (Integer) rmessage.values.get("Y"));
                            }
                        }
                    }

                    for (SensorMessage smessage : smessages) {
                        if (smessage != null) {
                            serverControl.setText("[SensorControl] Message from Sensor" + smessage);
                            if (smessage.getOp() == RobotMessage.ops.get("SETUPREPLY")) {
                                Sensor sensor = sensorControl.getSensor((Integer) smessage.values.get("ID"));
                                sensor.setPos((Integer) smessage.values.get("X"), (Integer) smessage.values.get("Y"));
                            }
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
