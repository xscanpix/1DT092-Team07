package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Basic TCP server adapter for receiving and sending over TCP socket connection.
 */
public class TcpNetworkAdapter {

    private ServerSocket serverSocket;
    private Socket socket;

    public TcpNetworkAdapter() {
    }

    public void initialize(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        socket = serverSocket.accept();
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
