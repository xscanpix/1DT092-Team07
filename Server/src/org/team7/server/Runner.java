package org.team7.server;

import org.team7.server.sensor.Sensor;
import org.team7.server.server.Server;
import org.team7.server.testclasses.RobotTest;
import org.team7.server.testclasses.SensorTest;

public class Runner {

    public static void main(String[] args) {
        Server server = new Server();
        server.initialize();
        Thread serverThread = server.start();

        RobotTest robotTest = new RobotTest(1, 1);
        robotTest.connect("localhost", 5555);
        robotTest.start(500);

        SensorTest sensorTest = new SensorTest(1, 1);
        SensorTest sensorTest2 = new SensorTest(2, 2);
        Sensor sensorTest3 = server.createOfflineSensor(3, 3);

        sensorTest.connect("localhost", 5556);
        sensorTest2.connect("localhost", 5556);

        sensorTest.start(2000);
        sensorTest2.start(2000);
        sensorTest3.start(2000);

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
