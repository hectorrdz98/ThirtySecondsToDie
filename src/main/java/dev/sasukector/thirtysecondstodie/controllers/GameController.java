package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;

import java.util.List;
import java.util.Random;

public class GameController {

    private static GameController instance = null;
    private @Getter int totalEvents = 0;
    private @Getter int remainingSeconds = 30;
    private @Getter Category currentCategory = Category.NORMAL;
    private @Getter Status currentStatus = Status.PAUSED;
    private int timerTaskID = -1;

    public enum Status {
        PAUSED, PLAYING
    }

    public enum Category {
        NORMAL, RARE, EPIC, LEGENDARY, GOD;
        private static final List<Category> VALUES = List.of(values());
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();
        public static Category randomCategory()  {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void startGame() {
        this.currentStatus = Status.PLAYING;
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
        this.currentCategory = Category.randomCategory();
        Bukkit.getOnlinePlayers().forEach(p -> p.playSound(p.getLocation(), "minecraft:block.note_block.xylophone", 1, 1));
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
