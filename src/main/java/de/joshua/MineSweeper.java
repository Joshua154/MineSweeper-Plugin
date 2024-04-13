package de.joshua;

import de.joshua.commands.MineSweeperCommands;
import de.joshua.game.MineSweeperGameManager;
import de.joshua.util.ui.GUIEH;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineSweeper extends JavaPlugin {
    private final MineSweeperGameManager gameManager = new MineSweeperGameManager();


    @Override
    public void onEnable() {
        this.getLogger().info("MineSweeper has been enabled!");

        // Register events
        this.getServer().getPluginManager().registerEvents(new GUIEH(), this);

        // Register commands
        this.getCommand("minesweeper").setExecutor(new MineSweeperCommands(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public MineSweeperGameManager getGameManager() {
        return gameManager;
    }
}
