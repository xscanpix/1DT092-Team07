package org.team7.server;

import org.team7.server.testclasses.RobotTest;
import org.team7.server.testclasses.SensorTest;
import org.team7.server.server.Server;

public class Runner {

    public static void main(String[] args) {
        Server server = new Server();
        server.initialize();
        Thread thread = server.start();

        RobotTest robotTest = new RobotTest();
        robotTest.connect("localhost", 5555);
        robotTest.start(500);

        SensorTest sensorTest = new SensorTest();
        sensorTest.connect("localhost", 5556);
        sensorTest.start(2000);

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
