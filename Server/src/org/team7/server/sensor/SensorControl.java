package org.team7.server.sensor;

import org.team7.server.network.TcpServerAdapter;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SensorControl {

    private TcpServerAdapter adapter;

    private boolean alive;
    private int port;

    private List<Sensor> sensors;
    private BlockingQueue<SensorMessage> in;

    public SensorControl(int port) {
        this.port = port;
        sensors = new ArrayList<>();
        in = new ArrayBlockingQueue<>(100);
    }

    public void addOfflineSensor(int x, int y) {
        sensors.add(new Sensor(x, y, false, null));
    }

    public SensorMessage pollMessage() {
        return in.poll();
    }

    private Thread start() {
        alive = true;

        Thread thread = new Thread(() -> {
            while (alive) {

                new Thread(() -> {
                    try {
                        Socket socket = adapter.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
    }

    public void initialize() {
        try {
            adapter = new TcpServerAdapter(port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        start();
    }
}
