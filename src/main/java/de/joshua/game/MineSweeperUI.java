package de.joshua.game;

import de.joshua.util.Location;
import de.joshua.util.fields.GameField;
import de.joshua.util.fields.GameFiledType;
import de.joshua.util.ui.IGUI;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class MineSweeperUI implements IGUI {
    private final MineSweeperGame game;
    private final Inventory inventory;

    public MineSweeperUI(MineSweeperGame game) {
        this.game = game;
        this.inventory =
                Bukkit.createInventory(this, 9 * 6, Component.text("Mine Sweeper"));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        int x = event.getSlot() % 9;
        int y = Math.floorDiv(event.getSlot(), 9);

        game.onClick(new Location(x, y), event.getClick());
    }

    @Override
    public void open(Player player) {
        refresh();
        player.openInventory(getInventory());
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

    }

    @Override
    public void refresh() {
        generateUI();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    private void generateUI() {
        GameField[][] gameField = game.getGameField();

        for (int x = 0; x < gameField.length; x++) {
            for (int y = 0; y < gameField[x].length; y++) {
                ItemStack item = gameField[x][y].getDisplayItem();
                inventory.setItem(x + y * 9, item);
            }
        }
    }
}
