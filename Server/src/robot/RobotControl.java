package robot;

import network.TcpServerAdapter;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class RobotControl {
    private TcpServerAdapter adapter;

    private List<Robot> robots;
    private BlockingQueue<Robot> newRobots;
    private int numRobots = 0;

    private BlockingQueue<RobotMessage> queueIn;
    private BlockingQueue<RobotMessage> queueOut;

    private int port;
    private boolean alive;

    private class Robot {
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        Robot(Socket socket) {
            this.socket = socket;
            try {
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        boolean isConnected() {
            return socket.isConnected();
        }

        public DataInputStream getIn() {
            return in;
        }

        public DataOutputStream getOut() {
            return out;
        }
    }

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

        start();
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

    private void start() {
        alive = true;

        new Thread(this::waitForConnection).start();

        Thread thread = new Thread(() -> {
            while (alive) {
                for (Robot robot : robots) {
                    if (robot.isConnected()) {
                        RobotMessage send;
                        try {
                            send = queueOut.poll(500, TimeUnit.MILLISECONDS);
                            if (send != null) {
                                System.out.println("[RobotControl] Sending data to Robot: " + send);
                                adapter.sendBytes(RobotMessage.encodeMessage(send), robot.out);
                            }
                        } catch (InterruptedException | RobotMessageException e) {
                            e.printStackTrace();
                        }
                        ByteBuffer receive = adapter.readBytes(robot.in);
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
