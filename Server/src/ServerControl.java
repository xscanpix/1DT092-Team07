import java.util.Scanner;

/**
 * Class for controlling the server. Define commands that can be input to the console.
 */
class ServerControl {

    private Server server;

    ServerControl(Server server) {
        this.server = server;
    }

    void start() {
        Thread thread = new Thread(() -> {
            Scanner sc = new Scanner(System.in);

            while (server.isAlive()) {
                String cmd = sc.nextLine();

                if (cmd.equals("quit")) {
                    server.stop();
                }
            }
        });
        thread.start();
    }
}
