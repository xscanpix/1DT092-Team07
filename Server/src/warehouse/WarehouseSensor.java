package warehouse;

import sensor.Sensor;

public class WarehouseSensor extends WarehouseObject {

    private Sensor sensor;

    public WarehouseSensor(int x, int y, Sensor sensor) {
        super(x, y);
        this.sensor = sensor;
    }
}
