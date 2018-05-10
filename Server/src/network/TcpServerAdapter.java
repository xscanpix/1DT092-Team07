package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Basic TCP server adapter for receiving and sending over TCP socket connection.
 */
public class TcpServerAdapter {

    private ServerSocket serverSocket;

    private DataInputStream in;
    private DataOutputStream out;

    private boolean isAlive;

    public TcpServerAdapter(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void accept() throws IOException {
        Socket socket = serverSocket.accept();
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());

        isAlive = true;
    }

    public boolean isConnected() {
        return isAlive;
    }

    public ByteBuffer readBytes() {

        ByteBuffer buf = null;

        if (isConnected()) {
            try {
                int len = in.readByte();

                buf = ByteBuffer.allocate(len);

                byte[] bytes = new byte[len];
                int read = in.read(bytes);

                buf.put(bytes);
                buf.rewind();
            } catch (EOFException e2) {
                isAlive = false;
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return buf;
    }

    public void sendBytes(ByteBuffer buf) {
        if (isConnected()) {
            try {

                out.writeByte(buf.array().length);
                out.write(buf.array());
            } catch (EOFException e2) {
                isAlive = false;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
