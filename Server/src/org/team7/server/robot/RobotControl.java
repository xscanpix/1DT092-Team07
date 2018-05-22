package org.team7.server.robot;

import org.team7.server.network.TcpServerAdapter;
import org.team7.server.message.robotmessage.RobotMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class RobotControl {
    private TcpServerAdapter adapter;

    private Map<Integer, Robot> robots;
    private BlockingQueue<RobotMessage> queueFromRobot;

    private int port;
    private boolean alive;

    public RobotControl(int port) {
        this.port = port;
        queueFromRobot = new ArrayBlockingQueue<>(50);
        robots = new ConcurrentHashMap<>();
    }

    public void initialize() {
        try {
            adapter = new TcpServerAdapter(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() {
        try {
            Socket socket = adapter.accept();
            Robot robot = new Robot(socket);
            robots.put(robot.id, robot);
            robot.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public List<RobotMessage> pollMessages() {
        List<RobotMessage> list = new ArrayList<>();

        queueFromRobot.drainTo(list);

        return list;
    }

    public Robot getRobot(int ID) {
        return robots.get(ID);
    }

    public void start() {
        alive = true;

        new Thread(() -> {
            while(alive) {
                waitForConnection();
            }
        }).start();

        new Thread(() -> {
            while(true) {
                try {
                    for(Map.Entry<Integer, Robot> entry : robots.entrySet()) {
                        RobotMessage msg = entry.getValue().getMessage();
                        if(msg != null) {
                            queueFromRobot.offer(msg);
                        }
                    }

                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
