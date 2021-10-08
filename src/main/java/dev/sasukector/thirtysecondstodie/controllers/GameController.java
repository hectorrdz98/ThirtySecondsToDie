package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import dev.sasukector.thirtysecondstodie.models.Event;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;

import java.util.*;
import java.util.stream.Collectors;

public class GameController {

    private static GameController instance = null;
    private @Getter int totalEvents = 0;
    private @Getter int remainingSeconds = 30;
    private @Getter Category currentCategory = Category.NORMAL;
    private @Getter Status currentStatus = Status.PAUSED;
    private int timerTaskID = -1;
    private @Getter final List<Event> activeEvents;

    public enum Status {
        PAUSED, PLAYING
    }

    public enum Category {
        NORMAL, RARE, EPIC, LEGENDARY, GOD;
        private static final Random RANDOM = new Random();
        public static Category randomCategory()  {
            double random = RANDOM.nextDouble();
            if (random < 0.1) return GOD;
            if (random < 0.2) return LEGENDARY;
            if (random < 0.4) return EPIC;
            if (random < 0.7) return RARE;
            return NORMAL;
        }
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public GameController() {
        this.activeEvents = new ArrayList<>();
    }

    public void startGame() {
        this.currentStatus = Status.PLAYING;
        this.activeEvents.clear();
        ServerUtilities.sendBroadcastMessage(ServerUtilities.getMiniMessage().parse("<bold><gradient:#9FE69E:#6FA16E>Ha iniciado el juego</gradient></bold>"));
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), "minecraft:block.note_block.bell", 1, 1);
            p.setStatistic(Statistic.DEATHS, 0);
        });
        runTimer();
    }

    public void stopGame() {
        this.currentStatus = Status.PAUSED;
        Bukkit.getScheduler().cancelTask(this.timerTaskID);
        ServerUtilities.sendBroadcastMessage(ServerUtilities.getMiniMessage().parse("<bold><gradient:#B57BA6:#7D5572>Se ha detenido el juego</gradient></bold>"));
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), "minecraft:block.note_block.bell", 1, 1));
    }

    public void newEvent() {
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), "minecraft:block.note_block.xylophone", 1, 1));
        List<Event> events = EventsController.getInstance().getEvents().stream()
                .filter(e -> e.getCategory() == this.currentCategory)
                .collect(Collectors.toList());
        Collections.shuffle(events);
        if (events.size() > 0) {
            EventsController.getInstance().handleNewEvent(events.get(0));
        } else {
            ServerUtilities.sendBroadcastMessage("§cNo hay eventos registrados para la categoría: " + ServerUtilities.getCategoryStyle(this.currentCategory));
        }
        this.currentCategory = Category.randomCategory();
    }

    public void runTimer() {
        this.timerTaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(ThirtySecondsToDie.getInstance(), () -> {
            this.remainingSeconds--;
            if (this.remainingSeconds <= 0) {
                this.totalEvents++;
                this.remainingSeconds = 30;
                newEvent();
            }
        }, 0L, 20L);
    }

}
