package de.joshua.microgame;

import de.joshua.MineSweeper;
import de.joshua.game.GameSettings;
import de.pianoman911.microgames.game.IMicroGame;
import de.pianoman911.microgames.game.IMicroGameMode;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class GameHandler implements IMicroGame {
    private final MineSweeper plugin;

    public GameHandler(MineSweeper plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getId() {
        return "minesweeper";
    }

    @Override
    public Component getName() {
        return Component.translatable("microgames.minesweeper.name");
    }

    @Override
    public void stopGame(Player player) {
        plugin.getGameManager().removeGame(player);
        player.closeInventory();
    }

    @Override
    public boolean startSingleplayer(Player player, @Nullable IMicroGameMode mode) {
        GameSettings settings = plugin.getDefaulConfig();
        plugin.getGameManager().createGame(plugin, player, settings);
        return true;
    }

    @Override
    public boolean isSingleplayer() {
        return true;
    }

}
