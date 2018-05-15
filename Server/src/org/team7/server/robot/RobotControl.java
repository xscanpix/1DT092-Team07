package org.team7.server.robot;

import org.team7.server.network.TcpServerAdapter;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpServerAdapter adapter;

    private List<Robot> robots;
    private BlockingQueue<Robot> newRobots;
    private int numRobots = 0;

    private BlockingQueue<RobotMessage> queueIn;
    private BlockingQueue<RobotMessage> queueOut;

    private int port;
    private boolean alive;

    public RobotControl(int port) {
        this.port = port;
        queueIn = new ArrayBlockingQueue<>(100);
        queueOut = new ArrayBlockingQueue<>(100);

        robots = new ArrayList<>();
        newRobots = new ArrayBlockingQueue<>(100);
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
            numRobots++;
            newRobots.offer(new Robot(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(int msSleepTime) {
        alive = true;

        new Thread(this::waitForConnection).start();

        Thread thread = new Thread(() -> {
            while (alive) {
                for (Robot robot : robots) {
                    if (robot.isConnected()) {
                        RobotMessage send;
                        try {
                            send = queueOut.poll();
                            if (send != null) {
                                System.out.println("[RobotControl] Sending data to Robot: " + send);
                                adapter.sendBytes(RobotMessage.encodeMessage(send), robot.getOut());
                            }
                        } catch (RobotMessageException e) {
                            e.printStackTrace();
                        } catch (EOFException e2) {
                            continue;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ByteBuffer receive = null;
                        try {
                            receive = adapter.readBytes(robot.getIn());
                        } catch (EOFException e2) {
                            continue;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (receive != null) {
                            try {
                                System.out.println("[RobotControl] Receiving data from Robot: " + RobotMessage.decodeMessage(receive));
                            } catch (RobotMessageException e) {
                                e.printStackTrace();
                            }
                            try {
                                queueIn.offer(RobotMessage.decodeMessage(receive));
                            } catch (RobotMessageException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                try {
                    Thread.sleep(msSleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                robots.addAll(newRobots);
            }
        });

        thread.start();

    }

    public RobotMessage pollMessage() {
        return queueIn.poll();
    }

    public boolean offerMessage(RobotMessage message) {
        return queueOut.offer(message);
    }
}
