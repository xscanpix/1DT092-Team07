package org.team7.server.robot;

import org.team7.server.network.TcpServerAdapter;
import org.team7.server.robot.robotmessage.RobotMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class RobotControl {
    private TcpServerAdapter adapter;

    private List<Robot> robots;

    private int port;
    private boolean alive;

    public RobotControl(int port) {
        this.port = port;
        robots = new ArrayList<>();
    }

    public void initialize() {
        try {
            adapter = new TcpServerAdapter(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() {
        try {
            Socket socket = adapter.accept();
            Robot robot = new Robot(socket);
            robots.add(robot);
            robot.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RobotMessage> pollMessages() {
        List<RobotMessage> messages = new ArrayList<>();

        for (Robot robot : robots) {
            messages.add(robot.getMessage());
        }

        return messages;
    }

    public void start(int msSleepTime) {
        alive = true;

        Thread thread = new Thread(() -> {
            while (alive) {
                waitForConnection();
            }
        });

        thread.start();
    }
}
