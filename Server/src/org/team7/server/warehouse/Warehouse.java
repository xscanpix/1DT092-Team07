package org.team7.server.warehouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the org.team7.server.warehouse and it's contents.
 */
public class Warehouse {

    public static char EMPTY = 'O';
    public static char ROBOT = 'R';
    public static char PACKAGE = 'P';
    public static char PATH = '-';

    private int width;
    private int height;

    /**
     * List of all objects.
     */
    private List<WarehouseObject> objects;

    /**
     * List of all paths.
     */
    private List<WarehousePath> paths;

    /**
     * Default constructor.
     */
    public Warehouse(int width, int height) {
        this.width = width;
        this.height = height;
        objects = new ArrayList<>();
        paths = new ArrayList<>();
    }

    public void addObject(WarehouseObject obj) {
        objects.add(obj);
    }

    public void addPath(int x1, int y1, int x2, int y2) {
        Tuple2<Integer> start = new Tuple2<>(x1, y1);
        Tuple2<Integer> end = new Tuple2<>(x2, y2);
        WarehousePath path = new WarehousePath(start, end);
        paths.add(path);
    }

    private char[] generateCharArray() {
        char[] result = new char[width * height];

        for (int i = 0; i < result.length; i++) {
            result[i] = EMPTY;
        }

        for (WarehouseObject obj : objects) {
            result[obj.getY() * width + obj.getX()] = obj.toChar();
        }

        for (WarehousePath path : paths) {
            Tuple2<Integer> start = path.getFirst();
            Tuple2<Integer> end = path.getSecond();

            if (start.getFirst().equals(end.getFirst()) && start.getSecond().equals(end.getSecond())) {
                result[height * start.getSecond() + start.getFirst()] = Warehouse.PATH;
            } else if (start.getFirst().equals(end.getFirst())) {
                if (start.getSecond() < end.getSecond()) {
                    for (int y = start.getSecond(); y < end.getSecond(); y++) {
                        result[height * y + start.getFirst()] = Warehouse.PATH;
                    }
                } else {
                    for (int y = end.getSecond(); y < start.getSecond(); y++) {
                        result[height * y + start.getFirst()] = Warehouse.PATH;
                    }
                }
            } else {
                if (start.getFirst() < end.getFirst()) {
                    for (int x = start.getFirst(); x < end.getFirst(); x++) {
                        result[height * start.getSecond() + x] = Warehouse.PATH;
                    }
                } else {
                    for (int x = end.getFirst(); x < start.getFirst(); x++) {
                        result[height * start.getSecond() + x] = Warehouse.PATH;
                    }
                }
            }
        }

        return result;
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        char[] array = generateCharArray();

        for (int i = 0; i < height; i++) {
            builder.append(array, width * i, width);
            builder.append("\n");
        }

        return builder.toString();
    }
}
