package org.team7.server.client;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.json.simple.JSONObject;
import org.team7.server.server.Server;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class ClientControl {

    private HttpServer httpServer;
    private static Server server;
    private int port;

    public ClientControl(int port, Server server) {
        this.port = port;
        ClientControl.server = server;
    }

    public void initialize() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress("127.0.0.1", port), 0);
            httpServer.createContext("/get/readings", new ClientGetReadingsHandler());
            httpServer.createContext("/get/robot", new ClientGetRobotInformationHandler());
            httpServer.createContext("/get/packages", new ClientGetPackageInformationHandler());
            httpServer.setExecutor(null);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientGetPackageInformationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = server.getPackageInformationAsJSON();

            exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);

            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    static class ClientGetRobotInformationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            String json = server.getRobotInformationAsJSON();

            exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);

            OutputStream os = exchange.getResponseBody();
            os.write(json.getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    static class ClientGetReadingsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            JSONObject obj = new JSONObject();

            obj.put("Readings", server.getSensorReadingsAsJSON(10));

            exchange.sendResponseHeaders(200, obj.toJSONString().getBytes(StandardCharsets.UTF_8).length);

            OutputStream os = exchange.getResponseBody();
            os.write(obj.toJSONString().getBytes(StandardCharsets.UTF_8));
            os.close();
        }
    }

    public void start() {
        httpServer.start();
    }
}
