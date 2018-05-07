package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class NetworkAdapter {

    private ServerSocket serverSocket;

    void initializeAdapter(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    protected Socket listen(int port) throws IOException {
        Socket socket = serverSocket.accept();

        return socket;
    }

    public abstract byte[] readBytes();

    public abstract void sendBytes(byte[] bytes);
}
