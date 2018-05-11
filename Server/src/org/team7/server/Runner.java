package org.team7.server;

import org.team7.server.robot.RobotTest;

public class Runner {

    public static void main(String[] args) {
        Server server = new Server();
        server.initialize();
        Thread thread = server.start();

        RobotTest robotTest = new RobotTest();
        robotTest.connect("127.0.0.1", 5555);

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
