package org.team7.server.sensor;

import org.team7.server.network.TcpServerAdapter;
import org.team7.server.sensor.sensormessage.SensorMessage;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SensorControl {
    private TcpServerAdapter adapter;

    private List<Sensor> sensors;

    private int port;
    private boolean alive;

    public SensorControl(int port) {
        this.port = port;
        sensors = new ArrayList<>();
    }

    public void initialize() {
        try {
            adapter = new TcpServerAdapter(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createOfflineSensor(int id, int x, int y) {
        Sensor sensor = new OfflineSensor(id, x, y);
        sensors.add(sensor);
    }

    private void waitForConnection() {
        try {
            Socket socket = adapter.accept();
            Sensor sensor = new OnlineSensor(socket);
            sensors.add(sensor);
            sensor.start(2000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addOfflineSensor(Sensor sensor) {
        sensors.add(sensor);
    }

    public List<SensorMessage> pollMessages() {
        List<SensorMessage> messages = new ArrayList<>();

        for (Sensor sensor : sensors) {
            messages.add(sensor.getMessage());
        }

        return messages;
    }

    public void start() {
        alive = true;

        new Thread(() -> {
            while (alive) {
                waitForConnection();
            }
        }).start();
    }
}
