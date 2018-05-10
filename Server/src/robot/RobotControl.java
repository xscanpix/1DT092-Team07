package robot;

import network.TcpServerAdapter;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpServerAdapter adapter;

    private BlockingQueue<RobotMessage> in;
    private BlockingQueue<RobotMessage> out;

    private boolean isAlive;

    public RobotControl() {
        in = new ArrayBlockingQueue<>(100);
        out = new ArrayBlockingQueue<>(100);
    }

    public void initialize(int port) {
        try {
            adapter = new TcpServerAdapter(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }

    private void waitForConnection() {
        try {
            System.out.println("[RobotControl] Waiting for connection...");
            adapter.accept();
            System.out.println("[RobotControl] Connection accepted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        isAlive = false;
    }

    private Thread start() {
        isAlive = true;

        Thread thread = new Thread(() -> {
            waitForConnection();

            while (isAlive) {
                if (adapter.isConnected()) {
                    RobotMessage send;
                    try {
                        send = out.poll(500, TimeUnit.MILLISECONDS);
                        if (send != null) {
                            System.out.println("[RobotControl] Sending data to Robot: " + send);
                            adapter.sendBytes(RobotMessage.encodeMessage(send));
                        }
                    } catch (InterruptedException | RobotMessageException e) {
                        e.printStackTrace();
                    }
                    ByteBuffer receive = adapter.readBytes();
                    if (receive != null) {
                        try {
                            System.out.println("[RobotControl] Receiving data from Robot: " + RobotMessage.decodeMessage(receive));
                        } catch (RobotMessageException e) {
                            e.printStackTrace();
                        }
                        try {
                            in.offer(RobotMessage.decodeMessage(receive));
                        } catch (RobotMessageException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });

        thread.start();

        return thread;
    }

    public RobotMessage pollMessage() {
        return in.poll();
    }

    public boolean offerMessage(RobotMessage message) {
        return out.offer(message);
    }
}
