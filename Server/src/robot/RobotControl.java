package robot;

import network.TcpServerAdapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpServerAdapter adapter;

    private BlockingQueue<String> in;
    private BlockingQueue<String> out;

    private boolean isAlive;

    public RobotControl() {
        in = new ArrayBlockingQueue<>(100);
        out = new ArrayBlockingQueue<>(100);

        adapter = new TcpServerAdapter();
    }

    public void initialize(int port) {
        try {
            adapter.initialize(port);
            isAlive = true;
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

    public Thread start() {
        Thread thread = new Thread(() -> {
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
                    System.out.println("[RobotControl] Receiving data from Robot: " + receive);
                    in.offer(receive);
                } else {
                    waitForConnection();
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
