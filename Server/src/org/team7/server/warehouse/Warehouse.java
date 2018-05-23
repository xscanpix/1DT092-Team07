package org.team7.server.warehouse;

import org.team7.server.message.Message;
import org.team7.server.message.robotmessage.RobotMessageMove;
import org.team7.server.sensor.Sensor;

import java.util.ArrayList;
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

    public static List<Message> generateMovesTo(int zoneID, boolean pickUp) {

        List<Message> moves = new ArrayList<>();

        if(zoneID == 2) {
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
        } else if(zoneID == 3) {
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
        } else if(zoneID == 4) {
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
        } else if(zoneID == 5) {
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
        } else if(zoneID == 6) {
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.FOLLOWLINE));
            moves.add(new RobotMessageMove(0, RobotMessageMove.LEFT));
            moves.add(new RobotMessageMove(0, RobotMessageMove.RIGHT180));
        } else if(zoneID == 7) {
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
        } else if(zoneID == 8) {
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
        } else if(zoneID == 9) {
            if(pickUp) {
                moves.add(new RobotMessageMove(0, RobotMessageMove.TAKELOAD));
            } else {
                moves.add(new RobotMessageMove(0, RobotMessageMove.LEAVELOAD));
            }
        }

        return moves;
    }

    private boolean addSensor(Sensor sensor) {
        if(!map.containsKey(sensor)) {
            map.put(sensor, null);
            return true;
        } else {
            return false;
        }
    }
}
