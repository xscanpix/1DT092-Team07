package org.team7.server.warehouse;

public abstract class WarehouseObject {

    private int x, y;

    WarehouseObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public abstract char toChar();
}
