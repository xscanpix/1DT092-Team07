package network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * Basic TCP server adapter for receiving and sending over TCP socket connection.
 */
public class TcpServerAdapter {

    /**
     * The server socket from where incoming connections are accepted.
     */
    private ServerSocket serverSocket;

    /**
     * Constructs a new TcpServerAdapter.
     *
     * @param port The port that the adapter listens on.
     * @throws IOException .
     */
    public TcpServerAdapter(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    /**
     * Blocks and waits for a new connection.
     *
     * @return The connected socket.
     * @throws IOException .
     */
    public Socket accept() throws IOException {
        return serverSocket.accept();
    }

    /**
     * Reads bytes from the input stream. The first byte of the input is the length of the message.
     *
     * @param in The input stream to read from.
     * @return Buffer of the message bytes
     * @throws IOException .
     */
    public ByteBuffer readBytes(DataInputStream in) throws IOException {
        int len = in.readByte();

        ByteBuffer buf = ByteBuffer.allocate(len);

        byte[] bytes = new byte[len];
        int read = in.read(bytes);

        buf.put(bytes);
        buf.rewind();

        return buf;
    }

    /**
     * Tries to send the buffered bytes to the output stream.
     *
     * @param buf The buffered bytes
     * @param out The output stream to write to.
     * @return true if write is successful, throw exception otherwise.
     * @throws IOException .
     */
    public boolean sendBytes(ByteBuffer buf, DataOutputStream out) throws IOException {
        out.writeByte(buf.array().length);
        out.write(buf.array());

        return true;
    }
}
