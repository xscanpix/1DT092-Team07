package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Basic TCP server adapter for receiving and sending over TCP socket connection.
 */
public class TcpServerAdapter {

    private ServerSocket serverSocket;

    public TcpServerAdapter(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    public ByteBuffer readBytes(DataInputStream in) {

        ByteBuffer buf = null;

        try {
            int len = in.readByte();

            buf = ByteBuffer.allocate(len);

            byte[] bytes = new byte[len];
            int read = in.read(bytes);

            buf.put(bytes);
            buf.rewind();
        } catch (EOFException e2) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buf;
    }

    public boolean sendBytes(ByteBuffer buf, DataOutputStream out) {
        try {
            out.writeByte(buf.array().length);
            out.write(buf.array());
        } catch (EOFException e2) {
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
