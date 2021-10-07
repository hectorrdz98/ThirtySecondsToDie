package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.helpers.FastBoard;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class BoardController {

    private static BoardController instance = null;
    private final Map<UUID, FastBoard> boards = new HashMap<>();

    public BoardController() {
        Bukkit.getScheduler().runTaskTimer(ThirtySecondsToDie.getInstance(), this::updateBoards, 0L, 20L);
    }

    public static BoardController getInstance() {
        if (instance == null) {
            instance = new BoardController();
        }
        return instance;
    }

    public void newPlayerBoard(Player player) {
        FastBoard board = new FastBoard(player);
        this.boards.put(player.getUniqueId(), board);
    }

    public void removePlayerBoard(Player player) {
        FastBoard board = this.boards.remove(player.getUniqueId());
        if (board != null) {
            board.delete();
        }
    }

    public void updateBoards() {
        boards.forEach((uuid, board) -> {
            Player player = Bukkit.getPlayer(uuid);
            assert player != null;

            board.updateTitle("§c§l☠ §4§lBits");
            if (GameController.getInstance().getCurrentStatus() == GameController.Status.PLAYING) {
                board.updateLines(
                        "",
                        "Jugador: §6" + player.getName(),
                        "Muertes: §c" + player.getStatistic(Statistic.DEATHS),
                        "",
                        "Próximo evento: §6" + GameController.getInstance().getRemainingSeconds() + "s",
                        "Categoría: " + ServerUtilities.getCategoryStyle(GameController.getInstance().getCurrentCategory())
                );
            } else {
                board.updateLines(
                        "",
                        "Jugador: §6" + player.getName(),
                        "Muertes: §c" + player.getStatistic(Statistic.DEATHS),
                        "",
                        "§cEl juego está en espera"
                );
            }
        });
    }

}
