package de.joshua.game;

public class GameSettings {
    private int width;
    private int height;
    private int mines;

    public GameSettings(int width, int height, int mines) {
        if (width > 9) throw new IllegalArgumentException("Width must be less than 9");
        if (height > 6) throw new IllegalArgumentException("Height must be less than 6");

        this.width = width;
        this.height = height;
        this.mines = mines;
    }

    public int width() {
        return width;
    }

    public void setWidth(int width) {
        if (width > 9) throw new IllegalArgumentException("Width must be less than 9");
        this.width = width;
    }

    public int height() {
        return height;
    }

    public void setHeight(int height) {
        if (height > 6) throw new IllegalArgumentException("Height must be less than 6");
        this.height = height;
    }

    public int mines() {
        return mines;
    }

    public void setMines(int mines) {
        this.mines = mines;
    }


    @Override
    public String toString() {
        return "GameSettings{" +
                "width=" + width +
                ", height=" + height +
                ", mines=" + mines +
                '}';
    }
}