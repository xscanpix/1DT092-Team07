package org.team7.server.robot;

import org.team7.server.message.robotmessage.RobotMessage;
import org.team7.server.network.TcpServerAdapter;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class RobotControl {
    private TcpServerAdapter adapter;

    private Robot robot;
    private BlockingQueue<RobotMessage> queueFromRobot;

    private int port;
    private boolean alive;

    public RobotControl(int port) {
        this.port = port;
        queueFromRobot = new ArrayBlockingQueue<>(50);
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
            this.robot = robot;
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

    public Robot getRobot() {
        return robot;
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
                    if(robot != null) {
                        RobotMessage msg = robot.getMessage();
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
