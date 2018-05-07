package client;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class ClientControl implements Runnable {

    private ClientNetworkAdapter adapter;

    private BlockingQueue<byte[]> in;
    private BlockingQueue<byte[]> out;

    public ClientControl() {

        in = new ArrayBlockingQueue<>(100);
        out = new ArrayBlockingQueue<>(100);

        adapter = new ClientNetworkAdapter(5555);
    }

    public byte[] takeBytes() {
        return in.poll();
    }

    public boolean sendBytes(byte[] bytes) {
        return out.offer(bytes);
    }

    @Override
    public void run() {
        byte[] send;
        try {
            send = out.poll(500, TimeUnit.MILLISECONDS);
            adapter.sendBytes(send);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        byte[] receive = adapter.readBytes();
        in.offer(receive);
    }
}
