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

    private void waitForConnection() {
        try {
            Socket socket = adapter.accept();
            Sensor sensor = new OnlineSensor(socket);
            sensors.add(sensor);
            sensor.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<SensorMessage> pollMessages() {
        List<SensorMessage> messages = new ArrayList<>();

        for (Sensor sensor : sensors) {
            messages.add(sensor.getReadings());
        }

        return messages;
    }

    public Thread start() {
        alive = true;

        Thread thread = new Thread(() -> {
            while (alive) {
                waitForConnection();
            }
        });

        thread.start();

        return thread;
    }
}
