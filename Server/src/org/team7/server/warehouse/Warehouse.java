package org.team7.server.warehouse;

import org.team7.server.sensor.Sensor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the org.team7.server.warehouse and it's contents.
 */
public class Warehouse {

    /**
     * List of all sensors.
     */
    private Map<Sensor, List<WarehousePackage>> map;

    /**
     * Default constructor.
     */
    public Warehouse() {
        map = new HashMap<>();
    }

    /**
     * Construct the warehouse according to specification
     */
    public void initialize() {
    }

    private boolean addSensor(Sensor sensor) {
        if (!map.containsKey(sensor)) {
            map.put(sensor, null);
            return true;
        } else {
            return false;
        }
    }
}
