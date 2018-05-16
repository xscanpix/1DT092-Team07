package org.team7.server.sensor;

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

public class SensorControl {
    private TcpServerAdapter adapter;

    private List<Sensor> sensors;
    private BlockingQueue<Sensor> newSensors;
    private int numSensors = 0;

    private BlockingQueue<SensorMessage> in;


    private int port;
    private boolean alive;

    public SensorControl(int port) {
        this.port = port;
        in = new ArrayBlockingQueue<>(100);


        sensors = new ArrayList<>();
        newSensors = new ArrayBlockingQueue<>(100);
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
            numSensors++;
            newSensors.offer(new Sensor(socket));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOfflineSensor(int x, int y) {
        sensors.add(new Sensor(x, y));
    }

    public void addOnlineSensor(int x, int y, Socket socket) {
        sensors.add(new Sensor(x, y, true, socket));
    }

    public SensorMessage pollMessage() {
        return in.poll();
    }

    public Thread start(int msSleepTime) {
        alive = true;

        new Thread(this::waitForConnection).start();

        Thread thread = new Thread(() -> {
            while (alive) {
                for (Sensor sensor : sensors) {
                    if (sensor.isConnected()) {
                        ByteBuffer receive = null;
                        try {
                            receive = adapter.readBytes(sensor.getIn());
                        } catch (EOFException e2) {
                            continue;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (receive != null) {
                            try {
                                System.out.println("[SensorControl] Receiving data from Sensor: " + SensorMessage.decodeMessage(receive))
                                ;
                            } catch (SensorMessageException e) {
                                e.printStackTrace();
                            }
                            try {
                                in.offer(SensorMessage.decodeMessage(receive));
                            } catch (SensorMessageException e) {
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

                sensors.addAll(newSensors);
            }
        });

        thread.start();

        return thread;
    }
}
