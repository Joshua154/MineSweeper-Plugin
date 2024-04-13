package de.joshua.util;

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
}
