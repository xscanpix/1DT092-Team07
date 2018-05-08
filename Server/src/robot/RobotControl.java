package robot;

import network.TcpServerAdapter;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpServerAdapter adapter;

    private BlockingQueue<String> in;
    private BlockingQueue<String> out;

    private Thread thread;

    private boolean isAlive;

    public RobotControl() {
        in = new ArrayBlockingQueue<>(100);
        out = new ArrayBlockingQueue<>(100);

        adapter = new TcpServerAdapter();
    }

    public void initialize(int port) {
        try {
            adapter.initialize(port);
            thread = start();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    String send;
                    try {
                        send = out.poll(500, TimeUnit.MILLISECONDS);
                        if (send != null) {
                            System.out.println("[RobotControl] Sending data to Robot: " + send);
                            adapter.sendStringUTF8asBytes(send);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String receive = adapter.readBytesAsStringUTF8();
                    if (receive != null) {
                        System.out.println("[RobotControl] Receiving data from Robot: " + receive);
                        in.offer(receive);
                    }
                }
            }
        });

        thread.start();

        return thread;
    }

    public String pollString() {
        return in.poll();
    }

    public boolean offerString(String string) {
        return out.offer(string);
    }
}
