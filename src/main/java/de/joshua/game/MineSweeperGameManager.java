package de.joshua.game;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Manages the Mine Sweeper games
 * @see MineSweeperGame
 */
public class MineSweeperGameManager {
    private final Map<Player, MineSweeperGame> games = new HashMap<>();

    public void createGame(Player player, GameSettings gameSettings) {
        MineSweeperGame game = new MineSweeperGame(gameSettings);
        game.setPlayer(player);
        game.open(player);

        games.put(player, game);
    }

    public void removeGame(Player player) {
        games.remove(player);
    }

    public MineSweeperGame getGame(Player player) {
        return games.get(player);
    }

    public boolean hasGame(Player player) {
        return games.containsKey(player);
    }
}
