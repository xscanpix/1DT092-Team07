package org.team7.server.server;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.team7.server.client.ClientControl;
import org.team7.server.message.Message;
import org.team7.server.message.robotmessage.RobotMessage;
import org.team7.server.robot.Robot;
import org.team7.server.robot.RobotControl;
import org.team7.server.sensor.Sensor;
import org.team7.server.sensor.SensorControl;

import java.util.ArrayList;
import java.util.List;

/*
 * Main entry point for the server.
 **/
public class Server {
    private static RobotControl robotControl;
    private static SensorControl sensorControl;
    private static ClientControl clientControl;
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
        clientControl = new ClientControl(8080, this);

        robotControl.initialize();
        sensorControl.initialize();
        clientControl.initialize();

        robotControl.start();
        sensorControl.start();
        clientControl.start();
    }

    public Robot getRobot() {
        return robotControl.getRobot();
    }

    public Sensor createOfflineSensor(int x, int y) {
        return sensorControl.createOfflineSensor(x, y);
    }

    public String getPackageInformationAsJSON() {
        JSONObject obj = new JSONObject();

        JSONObject p = new JSONObject();
        p.put("ID", 1);
        p.put("Location", List.of(1, 1));

        obj.put("Packages", List.of(p, p, p, p, p, p, p));
        return obj.toJSONString();
    }

    public String getRobotInformationAsJSON() {
        JSONObject obj = new JSONObject();

        obj.put("Position", JSONArray.toJSONString(List.of(1, 1)));
        obj.put("Direction", "Up");
        obj.put("Speed", 30);

        return obj.toJSONString();
    }

    public String getSensorReadingsAsJSON(int num) {
        List<Message> messages = sensorControl.pollReadings(num);

        List<JSONObject> objects = new ArrayList<>();

        for(Message message : messages) {
            JSONObject obj = new JSONObject();
            obj.put("ID", message.getValue("ID"));
            obj.put("R1", message.getValue("R1"));
            obj.put("R2", message.getValue("R2"));
            objects.add(obj);
        }

        return JSONArray.toJSONString(objects);
    }

    public Thread start() {
        alive = true;

        Thread thread = new Thread(() -> {
            while(alive) {
                try {
                    List<RobotMessage> rmessages = robotControl.pollMessages();

                    for(RobotMessage rmessage : rmessages) {
                        if(rmessage != null) {
                            if(rmessage.getOp() == RobotMessage.operations.get("SETUPREPLY")) {
                                Robot robot = robotControl.getRobot();
                                robot.setPos(rmessage.values.get("X"), rmessage.values.get("Y"));
                            }
                            if(rmessage.getOp() == RobotMessage.operations.get("MOVEREPLY")) {
                                Robot robot = robotControl.getRobot();
                                robot.setPos(2, 2);
                                serverControl.setText("[RobotControl] Robot done with follow lines: " + rmessage);
                            }
                            serverControl.setText("[RobotControl] Message from Robot: " + rmessage);
                        }
                    }


                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
    }

    boolean isAlive() {
        return alive;
    }
}
