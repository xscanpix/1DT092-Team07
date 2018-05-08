package robot;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

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

    public Thread start() {
        Thread thread = new Thread(() -> {
            connect();

            for (int i = 1; i <= 5; i++) {
                send("Message " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
    }

    public void send(String message) {
        if (socket != null && socket.isConnected()) {
            try {
                out.writeByte(message.getBytes().length);
                out.writeBytes(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String receive() {
        String res = null;
        if (socket != null && socket.isConnected()) {
            try {
                int len = in.readByte();
                byte[] bytes = new byte[1024];
                int result = in.read(bytes, 0, len);
                res = new String(bytes, StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return res;
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
