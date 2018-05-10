import robot.RobotControl;
import robot.RobotMessage;
import robot.RobotMessageException;
import robot.RobotTest;
import warehouse.Warehouse;
import warehouse.WarehousePackage;
import warehouse.WarehouseRobot;

import java.util.Scanner;

/*
 * Main entry point for the server.
 **/
public class Server {

    private static RobotControl robotControl;

    private static boolean isAlive;

    private Server() {
        /*
        Warehouse warehouse = new Warehouse(16, 16);
        warehouse.addObject(new WarehousePackage(0, 0));
        warehouse.addObject(new WarehouseRobot(1, 0));
        warehouse.addPath(3, 3, 6, 3);
        warehouse.addPath(6, 3, 6, 9);
        */

        robotControl = new RobotControl();
        robotControl.initialize(5555);
    }

    public static void main(String[] args) {
        Server server = new Server();
        Thread serverThread = server.start();

        Thread serverControlThread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);

            while (isAlive) {
                String cmd = sc.nextLine();

                if (cmd.equals("quit")) {
                    isAlive = false;
                }
            }
        });
        serverControlThread.start();

        /*
         * Test robot
         */
        RobotTest robotTest = new RobotTest("127.0.0.1", 5555);
        robotTest.connect();

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
            while (isAlive) {
                try {
                    RobotMessage msg = robotControl.pollMessage();
                    if (msg != null) {
                        System.out.println("[Server] Data from RobotControl: " + msg);
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
