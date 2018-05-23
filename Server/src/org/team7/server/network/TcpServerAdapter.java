package org.team7.server.network;

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
}
