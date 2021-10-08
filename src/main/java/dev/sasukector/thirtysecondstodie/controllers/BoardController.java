package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.helpers.FastBoard;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import dev.sasukector.thirtysecondstodie.models.Event;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

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

            board.updateTitle("§4§l30 §c§l☠ §4§lsegundos");
            if (GameController.getInstance().getCurrentStatus() == GameController.Status.PLAYING) {
                List<String> lines = new ArrayList<>();
                lines.add("");
                lines.add("Jugador: §6" + player.getName());
                lines.add("Muertes: §c" + player.getStatistic(Statistic.DEATHS));
                lines.add("");
                lines.add("Próximo evento: §6" + GameController.getInstance().getRemainingSeconds() + "s");
                lines.add("Categoría: " + ServerUtilities.getCategoryStyle(GameController.getInstance().getCurrentCategory()));
                if (GameController.getInstance().getActiveEvents().size() > 0) {
                    lines.add("");
                    lines.add("Eventos activos: ");
                    for (Event event : GameController.getInstance().getActiveEvents().stream()
                            .sorted(Comparator.comparingInt(Event::getMissingTime))
                            .limit(3).collect(Collectors.toList())) {
                        lines.add(ServerUtilities.getCategoryColor(event.getCategory()) + event.getName() + " §7" +
                                event.getMissingTime() + "s");
                    }
                    if (GameController.getInstance().getActiveEvents().size() > 3) {
                        lines.add("+" + (GameController.getInstance().getActiveEvents().size() - 3) + " eventos extra");
                    }
                }
                board.updateLines(lines);
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
