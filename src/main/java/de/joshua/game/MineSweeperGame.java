package de.joshua.game;

import de.joshua.util.Location;
import de.joshua.util.fields.GameField;
import de.joshua.util.fields.GameFiledType;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MineSweeperGame {
    private final GameField[][] gameField;
    private final List<Location> flaggedLocations = new ArrayList<>();
    private final List<Location> mines = new ArrayList<>();
    private final MineSweeperUI ui = new MineSweeperUI(this);
    private final GameSettings gameSettings;
    private Player player;
    private boolean isRunning = true;

    public MineSweeperGame(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.gameField = new GameField[gameSettings.width()][gameSettings.height()];

        for (int x = 0; x < gameSettings.width(); x++) {
            for (int y = 0; y < gameSettings.height(); y++) {
                gameField[x][y] = new GameField(GameFiledType.EMPTY);
            }
        }

        generateMines();
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    protected void onClick(Location clickedLocation, ClickType clickType) {
        if (!isRunning) return;

        GameField clickedField = clickedLocation.getAtLocation(gameField);

        if (clickType.isLeftClick()) {
            if (clickedField.isFlagged()) return;

            if (clickedField.getType() == GameFiledType.MINE) {
                player.sendMessage(Component.text("You lost!"));
                revealAll();
                isRunning = false;

                ui.refresh();
                return;
            }

            revealEmptyFields(clickedLocation);
        } else if (clickType.isRightClick()) {
            if (clickedField.isFlagged()) flaggedLocations.remove(clickedLocation);
            else flaggedLocations.add(clickedLocation);

            clickedField.setFlagged(!clickedField.isFlagged());

            if (flaggedLocations.size() == gameSettings.mines() && mines.stream().allMatch(mine -> mine.getAtLocation(gameField).isFlagged())) {
                player.sendMessage(Component.text("You won!"));
                isRunning = false;
                revealAll();
            }
        }


        ui.refresh();
    }

    private void revealAll() {
        for (int x = 0; x < gameSettings.width(); x++) {
            for (int y = 0; y < gameSettings.height(); y++) {
                gameField[x][y].setRevealed(true);
            }
        }
    }

    public void open(Player player) {
        ui.open(player);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void generateMines() {
        List<Location> locations = new ArrayList<>();
        for (int x = 0; x < gameSettings.width(); x++) {
            for (int y = 0; y < gameSettings.height(); y++) {
                locations.add(new Location(x, y));
            }
        }

        Collections.shuffle(locations);
        List<Location> selectedLocations = locations.subList(0, gameSettings.mines());
        this.mines.clear();
        this.mines.addAll(selectedLocations);

        for (Location location : selectedLocations) {
            location.getAtLocation(gameField).setType(GameFiledType.MINE);

            for (int ox = -1; ox <= 1; ox++) {
                for (int oy = -1; oy <= 1; oy++) {
                    Location offsetLocation = location.offset(ox, oy);

                    if (isValideLocation(offsetLocation)) {
                        offsetLocation.getAtLocation(gameField).addMineAround();
                    }
                }
            }
        }
    }

    private void revealEmptyFields(Location location) {
        GameField currentField = location.getAtLocation(gameField);
        currentField.setRevealed(true);

        if (currentField.getMinesAround() != 0)
            return;

        int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        for (int[] d : dir) {
            Location newLocation = location.offset(d[0], d[1]);
            if (!isValideLocation(newLocation)) continue;

            GameField newField = newLocation.getAtLocation(gameField);
            if (newField.getType() != GameFiledType.EMPTY || newField.isRevealed()) continue;

            newField.setRevealed(true);
            if (newField.getMinesAround() == 0) revealEmptyFields(newLocation);
        }
    }

    public boolean isValideLocation(Location location) {
        return location.getX() >= 0 && location.getX() < gameSettings.width() && location.getY() >= 0 && location.getY() < gameSettings.height();
    }

    public GameField[][] getGameField() {
        return gameField.clone();
    }
}
