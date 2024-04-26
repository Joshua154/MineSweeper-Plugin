package de.joshua.game;

import de.joshua.MineSweeper;
import de.joshua.util.Location;
import de.joshua.util.fields.GameField;
import de.joshua.util.ui.IGUI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Represents the UI of the Mine Sweeper game
 * @see IGUI
 */
public class MineSweeperUI implements IGUI {
    private final MineSweeperGame game;
    private final Inventory inventory;

    public MineSweeperUI(MineSweeperGame game) {
        this.game = game;
        this.inventory = Bukkit.createInventory(this, 6 * 9, Component.translatable("microgames.minesweeper.gui-title"));
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Location location = Location.of(event.getSlot());

        game.onClick(location, event.getClick());
    }

    @Override
    public void open(Player player) {
        refresh();
        player.openInventory(getInventory());
    }

    @Override
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        MineSweeper mineSweeper = game.getMineSweeper();

        if(!mineSweeper.getGameManager().hasGame(player)) return;
        mineSweeper.getConnector().cancelGame(game.getMineSweeper().getGameHandler(), player, () -> {
            mineSweeper.getGameManager().getGame(player).open(player);
        });
    }

    @Override
    public void refresh() {
        generateUI();
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    // generate the content of the inventory
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
