import robot.RobotControl;
import robot.RobotMessage;
import robot.RobotTest;
import sensor.SensorControl;

/*
 * Main entry point for the server.
 **/
public class Server {

    private static final Server instance = new Server();

    private static RobotControl robotControl;
    private static SensorControl sensorControl;
    private static ServerControl serverControl;

    private static boolean alive;

    private Server() {

    }

    private void initialize() {
        serverControl = new ServerControl(instance);
        robotControl = new RobotControl(5555);
        sensorControl = new SensorControl(5556);

        robotControl.initialize();
        sensorControl.initialize();
    }

    public static void main(String[] args) {
        instance.initialize();
        Thread serverThread = instance.start();

        /*
         * Test robot
         */
        RobotTest robotTest = new RobotTest();
        robotTest.connect("127.0.0.1", 5555);

        try {
            serverThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private Thread start() {
        alive = true;

        serverControl.start();

        /*
         * Test thread that polls the queue in robot control for messages from robots.
         * */
        Thread thread = new Thread(() -> {
            while (alive) {
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

    void stop() {
        alive = false;
    }

    boolean isAlive() {
        return alive;
    }
}
