import warehouse.Warehouse;
import warehouse.WarehousePackage;
import warehouse.WarehouseRobot;

/*
* Main entry point for the server.
**/
public class Server {

    public static void main(String[] args) {

        Warehouse w = new Warehouse(16, 16);
        w.addObject(new WarehousePackage(0,0));
        w.addObject(new WarehouseRobot(1, 0));
        System.out.print(w);

    }
}
