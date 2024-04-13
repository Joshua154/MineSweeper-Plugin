package de.joshua;

import org.bukkit.plugin.java.JavaPlugin;

public final class MineSweeper extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getLogger().info("MineSweeper has been enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
