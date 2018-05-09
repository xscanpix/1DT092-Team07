package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Basic TCP server adapter for receiving and sending over TCP socket connection.
 */
public class TcpServerAdapter {

    private ServerSocket serverSocket;
    private Socket socket;

    private DataInputStream in;
    private DataOutputStream out;
    private byte[] bytes;

    private boolean isAlive;

    public TcpServerAdapter(int port) throws IOException {
            serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        socket = serverSocket.accept();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        bytes = new byte[1024];

        isAlive = true;
    }

    public boolean isConnected() {
        return isAlive;
    }

    private byte[] readBytes() {
        if (isConnected()) {
            try {
                int len = in.readByte();
                in.read(bytes, 0, len);
            } catch (EOFException e2) {
                isAlive = false;
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    public String readBytesAsStringUTF8() {
        byte[] bytes = readBytes();

        String s = null;

        if (bytes != null) {
            s = new String(bytes, StandardCharsets.UTF_8);
        }

        return s;
    }

    private void sendBytes(byte[] bytes) {
        if (isConnected()) {
            try {
                int len = bytes.length;
                out.writeByte(len);
                out.write(bytes, 0, len);
            } catch (EOFException e2) {
                isAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendStringUTF8asBytes(String message) {
        sendBytes(message.getBytes());
    }
}
