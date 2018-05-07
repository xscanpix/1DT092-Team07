package robot;

import network.TcpNetworkAdapter;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class RobotControl {
    private TcpNetworkAdapter adapter;

    private BlockingQueue<byte[]> in;
    private BlockingQueue<byte[]> out;

    public RobotControl() {
        in = new ArrayBlockingQueue<>(100);
        out = new ArrayBlockingQueue<>(100);

        adapter = new TcpNetworkAdapter();
    }

    public void initialize(int port) {
        try {
            adapter.initialize(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void waitForConnection() {
        try {
            adapter.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        new Thread(() -> {
            byte[] send;
            try {
                send = out.poll(500, TimeUnit.MILLISECONDS);
                adapter.sendBytes(send);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            byte[] receive = adapter.readBytes();
            in.offer(receive);

        }).start();
    }

    public byte[] pollBytes() {
        return in.poll();
    }

    public boolean offerBytes(byte[] bytes) {
        return out.offer(bytes);
    }
}
