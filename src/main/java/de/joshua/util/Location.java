package de.joshua.util;

// Location class to represent a 2D location
public class Location {
    private final int x;
    private final int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Location{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public <T> T getAtLocation(T[][] grid) {
        return grid[x][y];
    }

    public Location offset(int ox, int oy) {
        return new Location(x + ox, y + oy);
    }

    public static Location of(int x, int y) {
        return new Location(x, y);
    }

    // calculate the Location from the slot in the inventory
    public static Location of(int slotIndex) {
        int x = slotIndex % 9;
        int y = Math.floorDiv(slotIndex, 9);

        return Location.of(x, y);
    }
}
