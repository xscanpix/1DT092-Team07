package warehouse;

public class WarehouseRobot extends WarehouseObject {

    public WarehouseRobot(int x, int y) {
        super(x, y);
    }

    @Override
    public char toChar() {
        return Warehouse.ROBOT;
    }
}
