package client;

import network.NetworkAdapter;

import java.io.IOException;
import java.net.Socket;

public class ClientNetworkAdapter extends NetworkAdapter {

    private Socket socket;

    private int port;

    public ClientNetworkAdapter(int port) {
        this.port = port;
    }

    public void listen() {
        try {
            socket = listen(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] readBytes() {
        byte[] bytes = {};
        if (socket != null && socket.isConnected()) {
            try {
                bytes = socket.getInputStream().readAllBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public void sendBytes(byte[] bytes) {
        if (socket != null && socket.isConnected()) {
            try {
                socket.getOutputStream().write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
