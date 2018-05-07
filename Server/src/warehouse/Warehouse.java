package warehouse;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the warehouse and it's contents.
 */
public class Warehouse {

    private int width;
    private int height;
    /**
     * List of all moving objects.
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
        for(int i = 0; i < width * height; i++) {
            objects.add(i, null);
        }
        paths = new ArrayList<>();
    }

    public void addObject(WarehouseObject obj) {
        objects.add((obj.getY() * width) + obj.getX(), obj);
    }

    public void addPath(int x1, int y1, int x2, int y2) {
        WarehousePath path = new WarehousePath(x1, y1, x2, y2);
        paths.add(path);
    }

    public String toString() {

        StringBuilder builder = new StringBuilder();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                if(objects.get((y * height) + x) == null) {
                    builder.append(" ");
                } else {
                    builder.append(objects.get((y * height) + x).toString());
                }
            }
            builder.append('\n');
        }

        return builder.toString();
    }
}
