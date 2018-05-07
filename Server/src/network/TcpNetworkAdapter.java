package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Basic TCP server adapter for receiving and sending over TCP socket connection.
 */
public class TcpNetworkAdapter {

    private ServerSocket serverSocket;
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;
    private byte[] bytes;

    public TcpNetworkAdapter() {
    }

    public void initialize(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        socket = serverSocket.accept();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        bytes = new byte[1024];
    }

    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    public byte[] readBytes() {
        if (socket != null && socket.isConnected()) {
            try {
                int len = in.readByte();
                in.read(bytes, 0, len);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public void sendBytes(byte[] bytes) {
        if (isConnected()) {
            try {
                socket.getOutputStream().write(bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
