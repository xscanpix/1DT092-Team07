package warehouse;

public class WarehousePackage extends WarehouseObject {

    public WarehousePackage(int x, int y) {
        super(x, y);
    }

    @Override
    public char toChar() {
        return Warehouse.PACKAGE;
    }
}
