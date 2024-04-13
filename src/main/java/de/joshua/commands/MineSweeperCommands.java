package de.joshua.commands;

import de.joshua.MineSweeper;
import de.joshua.game.GameSettings;
import net.kyori.adventure.text.Component;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MineSweeperCommands implements TabExecutor {
    private final MineSweeper plugin;

    public MineSweeperCommands(MineSweeper plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) return false;

        if (strings.length == 0) {
            player.sendMessage("Usage: /minesweeper <reconnect|start>");
            return false;
        }

        switch (strings[0]) {
            case "reconnect" -> plugin.getGameManager().getGame(player).open(player);
            case "start" -> {
                if (strings.length > 4) {
                    return false;
                }

                GameSettings settings = new GameSettings(9, 6, 10);

                if (strings.length > 1) {
                    try {
                        settings.setWidth(Integer.parseInt(strings[1]));
                    } catch (Exception e) {
                        player.sendMessage(Component.text(e.getMessage()));
                        return false;
                    }
                }
                if (strings.length > 2) {
                    try {
                        settings.setHeight(Integer.parseInt(strings[2]));
                    } catch (Exception e) {
                        player.sendMessage(Component.text(e.getMessage()));
                        return false;
                    }
                }
                if (strings.length > 3) settings.setMines(Integer.parseInt(strings[3]));

                plugin.getGameManager().createGame(player, settings);
            }
            default -> player.sendMessage("Usage: /minesweeper <reconnect|start>");
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (strings.length != 1) return List.of();
        return List.of("reconnect", "start");
    }
}
