import robot.RobotControl;
import robot.RobotTest;
import warehouse.Warehouse;
import warehouse.WarehousePackage;
import warehouse.WarehouseRobot;

import java.awt.*;

/*
 * Main entry point for the server.
 **/
public class Server {

    private static RobotControl robot;

    public static void main(String[] args) {


        Warehouse w = new Warehouse(16, 16);
        w.addObject(new WarehousePackage(0, 0));
        w.addObject(new WarehouseRobot(1, 0));
        System.out.print(w);

        robot = new RobotControl();
        robot.initialize(5555);
        Thread robotthread = robot.start();

        RobotTest test = new RobotTest("127.0.0.1", 5555);
        test.connect();
        String message = "Test";
        test.send(message);

        try {
            robotthread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
