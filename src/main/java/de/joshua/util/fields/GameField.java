package de.joshua.util.fields;

import de.joshua.util.builder.ItemBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

// Represents a field in the game
public class GameField {
    private GameFiledType type;
    private boolean isFlagged;
    private boolean isRevealed = false;

    private GameFieldItemBuilders displayItem;
    private int minesAround;

    public GameField(GameFiledType type) {
        this.type = type;
        this.minesAround = 0;
        update();
    }

    // Getters and setters
    public GameFiledType getType() {
        return type;
    }

    public void setType(GameFiledType gameFiledType) {
        this.type = gameFiledType;
        update();
    }

    public int getMinesAround() {
        return minesAround;
    }

    public void addMineAround() {
        this.minesAround++;
        update();
    }

    public void addMineAround(int i) {
        this.minesAround += i;
        update();
    }

    protected ItemBuilder getItemStackBuilder() {
        return displayItem.getItemBuilder();
    }

    public ItemStack getDisplayItem() {
        return displayItem.getItemBuilder().build();
    }

    public boolean isFlagged() {
        return isFlagged;
    }

    public void setFlagged(boolean flagged) {
        isFlagged = flagged;
        update();
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
        update();
    }

    // Update the display item
    private void update() {
        if (!isRevealed) {
            if (isFlagged)
                displayItem = GameFieldItemBuilders.FLAGGED;
            else
                displayItem = GameFieldItemBuilders.HIDDEN;
        } else {
            switch (type) {
                case MINE -> displayItem = GameFieldItemBuilders.MINE;
                case EMPTY -> displayItem = GameFieldItemBuilders.getNumber(minesAround);
            }
        }
    }

    // Enum to represent the different types of items in the game field
    public enum GameFieldItemBuilders {
        MINE(new ItemBuilder(Material.TNT).displayName(Component.text("Mine"))),
        NUMBER_1(new ItemBuilder(Material.LIME_STAINED_GLASS_PANE).displayName(Component.text("1")).customModelData(1)),
        NUMBER_2(new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE).displayName(Component.text("2")).customModelData(1)),
        NUMBER_3(new ItemBuilder(Material.YELLOW_STAINED_GLASS_PANE).displayName(Component.text("3")).customModelData(1)),
        NUMBER_4(new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE).displayName(Component.text("4")).customModelData(1)),
        NUMBER_5(new ItemBuilder(Material.PINK_STAINED_GLASS_PANE).displayName(Component.text("5")).customModelData(1)),
        NUMBER_6(new ItemBuilder(Material.MAGENTA_STAINED_GLASS_PANE).displayName(Component.text("6")).customModelData(1)),
        NUMBER_7(new ItemBuilder(Material.PURPLE_STAINED_GLASS_PANE).displayName(Component.text("7")).customModelData(1)),
        NUMBER_8(new ItemBuilder(Material.RED_STAINED_GLASS_PANE).displayName(Component.text("8")).customModelData(1)),
        HIDDEN(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).displayName(Component.text("")).customModelData(1)),
        FLAGGED(new ItemBuilder(Material.BLUE_STAINED_GLASS_PANE).displayName(Component.text("Flagged")).customModelData(1)),
        EMPTY(new ItemBuilder());

        private final ItemBuilder itemBuilder;

        GameFieldItemBuilders(ItemBuilder itemBuilder) {
            this.itemBuilder = itemBuilder;
        }

        public static GameFieldItemBuilders getNumber(int number) {
            return switch (number) {
                case 0 -> EMPTY;
                case 1 -> NUMBER_1;
                case 2 -> NUMBER_2;
                case 3 -> NUMBER_3;
                case 4 -> NUMBER_4;
                case 5 -> NUMBER_5;
                case 6 -> NUMBER_6;
                case 7 -> NUMBER_7;
                case 8 -> NUMBER_8;
                default -> throw new IllegalStateException("Unexpected value: " + number);
            };
        }

        @NotNull
        public ItemBuilder getItemBuilder() {
            return itemBuilder;
        }
    }
}
