package de.joshua;

import de.joshua.game.GameSettings;
import de.joshua.game.MineSweeperGameManager;
import de.joshua.microgame.GameHandler;
import de.joshua.util.ui.GUIEH;
import de.pianoman911.microgames.api.IGameConnector;
import de.pianoman911.microgames.api.IGameRegistry;
import de.pianoman911.microgames.game.IMicroGame;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MineSweeper extends JavaPlugin {
    private final MineSweeperGameManager gameManager = new MineSweeperGameManager();
    private IGameRegistry registry;
    private IGameConnector connector;
    private GameHandler gameHandler;


    @Override
    public void onEnable() {
        registry = Bukkit.getServicesManager().load(IGameRegistry.class);
        connector = Bukkit.getServicesManager().load(IGameConnector.class);
        if (registry == null || connector == null) {
            throw new RuntimeException("Could not load services!");
        }

        gameHandler = new GameHandler(this);
        if (!registry.registerGame(gameHandler)) {
            throw new RuntimeException("Could not register game!");
        }



        this.getLogger().info("MineSweeper has been enabled!");

        // Register events
        this.getServer().getPluginManager().registerEvents(new GUIEH(), this);
    }

    @Override
    public void onDisable() {
        registry.unregisterGame("minesweeper");
    }

    public MineSweeperGameManager getGameManager() {
        return gameManager;
    }

    public GameSettings getDefaulConfig() {
        return new GameSettings(9, 6, 8);
    }

    public IGameRegistry getRegistry() {
        return registry;
    }

    public IGameConnector getConnector() {
        return connector;
    }

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
