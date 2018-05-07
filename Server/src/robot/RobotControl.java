package robot;

import network.TcpNetworkAdapter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpNetworkAdapter adapter;

    private BlockingQueue<byte[]> in;
    private BlockingQueue<byte[]> out;

    private boolean isAlive;

    public RobotControl() {
        in = new ArrayBlockingQueue<>(100);
        out = new ArrayBlockingQueue<>(100);

        adapter = new TcpNetworkAdapter();
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
            System.out.println("RobotControl: Waiting for connection.");
            adapter.accept();
            System.out.println("RobotControl: Connection accepted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Thread start() {
        Thread thread = new Thread(() -> {

            while (isAlive) {
                if (adapter.isConnected()) {
                    byte[] send;
                    try {
                        send = out.poll(500, TimeUnit.MILLISECONDS);
                        System.out.println("RobotControl: Sending data: " + send);
                        if (send != null) {
                            adapter.sendBytes(send);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println("Reading data.");
                    byte[] receive = adapter.readBytes();
                    System.out.println("Read data: " + new String(receive, StandardCharsets.UTF_8));
                    in.offer(receive);
                } else {
                    waitForConnection();
                }
            }
        });

        thread.start();

        return thread;
    }

    public byte[] pollBytes() {
        return in.poll();
    }

    public boolean offerBytes(byte[] bytes) {
        return out.offer(bytes);
    }
}
