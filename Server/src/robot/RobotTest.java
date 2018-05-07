package robot;

import java.io.*;
import java.net.Socket;

public class RobotTest {
    private int port;
    private String host;
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;

    public RobotTest(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void connect() {
        try {
            socket = new Socket(host, port);
            System.out.println("RobotTest: " + socket.getLocalAddress() + " " + socket.getLocalPort());
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(String message) {
        if(socket != null && socket.isConnected()) {
            try {
                out.writeByte(message.getBytes().length);
                out.writeBytes(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
