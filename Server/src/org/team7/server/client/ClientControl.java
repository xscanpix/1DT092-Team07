package org.team7.server.client;

import org.json.simple.JSONObject;
import org.team7.server.network.TcpServerAdapter;

import java.io.IOException;
import java.net.Socket;

public class ClientControl {

    private TcpServerAdapter adapter;
    private Socket socket;

    private int port;

    public ClientControl(int port) {
        this.port = port;
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
            socket = adapter.accept();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject createJSON(int test) {
        JSONObject obj = new JSONObject();

        return null;
    }

    public void start() {
        new Thread(() -> {
            while(true) {
                waitForConnection();

                // Read request and handle it

                try {
                    socket.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
