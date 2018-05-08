import robot.RobotControl;
import robot.RobotTest;
import warehouse.Warehouse;
import warehouse.WarehousePackage;
import warehouse.WarehouseRobot;

import java.awt.*;
import java.nio.charset.StandardCharsets;

/*
 * Main entry point for the server.
 **/
public class Server {

    private static Warehouse warehouse;
    private static Server server;
    private static RobotControl robotControl;
    private static RobotTest robotTest;

    private static Thread serverThread;
    private static Thread robotControlThread;
    private static Thread robotThread;

    private boolean isAlive;

    public Server() {
        warehouse = new Warehouse(16, 16);
        warehouse.addObject(new WarehousePackage(0, 0));
        warehouse.addObject(new WarehouseRobot(1, 0));

        robotControl = new RobotControl();
        robotControl.initialize(5555);
    }



    public static void main(String[] args) {
        server = new Server();
        serverThread = server.start();
        robotControlThread = robotControl.start();

        robotTest = new RobotTest("127.0.0.1", 5555);
        robotThread = robotTest.start();

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    private Thread start() {
        isAlive = true;

        Thread thread = new Thread(() -> {
            while(isAlive)  {
                try {
                    String s = robotControl.pollString();
                    if(s != null) {
                        System.out.println("[Server] Data from RobotControl: " + s);
                    }
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        return thread;
    }
}
