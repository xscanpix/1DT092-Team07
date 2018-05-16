package org.team7.server.robot;

import org.team7.server.network.TcpServerAdapter;
import org.team7.server.robot.robotmessage.RobotMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpServerAdapter adapter;

    private Map<Integer, Robot> robots;

    private BlockingQueue<RobotMessage> queueOut;

    private int port;
    private boolean alive;

    public RobotControl(int port) {
        this.port = port;
        queueOut = new ArrayBlockingQueue<>(100);
        robots = new ConcurrentHashMap<>();
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
            robots.put(robot.id, robot);
            robot.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<RobotMessage> pollMessages() {
        List<RobotMessage> messages = new ArrayList<>();

        for (Map.Entry<Integer, Robot> entry : robots.entrySet()) {
            messages.add(entry.getValue().getMessage());
        }

        return messages;
    }

    public Robot getRobot(int ID) {
        return robots.get(ID);
    }

    public void start() {
        alive = true;

        new Thread(() -> {
            while (alive) {
                waitForConnection();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                try {
                    RobotMessage msg = queueOut.poll(100, TimeUnit.MILLISECONDS);

                    if (msg != null) {
                        int id = (int) msg.values.get("ID");

                        Robot robot = robots.get(id);
                        robot.sendMessage(msg.encodeMessage());
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
