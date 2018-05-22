package org.team7.server.sensor;

import org.team7.server.message.Message;
import org.team7.server.message.sensormessage.SensorMessage;
import org.team7.server.network.TcpServerAdapter;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public class SensorControl {
    private TcpServerAdapter adapter;

    private Map<Integer, Sensor> sensors;
    private BlockingQueue<SensorMessage> queueFromSensor;

    private int port;
    private boolean alive;

    public SensorControl(int port) {
        this.port = port;
        queueFromSensor = new ArrayBlockingQueue<>(50);
        sensors = new ConcurrentHashMap<>();
    }

    public void initialize() {
        try {
            adapter = new TcpServerAdapter(port);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForConnection() {
        try {
            Socket socket = adapter.accept();
            Sensor sensor = new OnlineSensor(socket);
            sensors.put(sensor.id, sensor);
            sensor.start(2000);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public List<Message> pollReadings(int num) {
        List<Message> list = new ArrayList<>();

        queueFromSensor.drainTo(list, num);

        return list;
    }

    public Sensor getSensor(int ID) {
        return sensors.get(ID);
    }

    public Sensor createOfflineSensor(int x, int y) {
        Sensor sensor = new OfflineSensor(x, y);
        sensors.put(sensor.id, sensor);
        return sensor;
    }

    public void start() {
        alive = true;

        new Thread(() -> {
            while(alive) {
                waitForConnection();
            }
        }).start();

        new Thread(() -> {
            while(true) {
                for(Map.Entry<Integer, Sensor> entry : sensors.entrySet()) {
                    SensorMessage msg = entry.getValue().pollMessage();
                    if(msg != null) {
                        queueFromSensor.offer(msg);
                    }
                }

                try {
                    Thread.sleep(500);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
