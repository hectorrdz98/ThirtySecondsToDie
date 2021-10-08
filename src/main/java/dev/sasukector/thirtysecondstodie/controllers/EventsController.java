package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.models.Event;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventsController {

    public static EventsController instance = null;
    private @Getter final List<Event> events;
    private final Random random;

    public enum EventType {
        ZOMBIE_NORMAL, SKELETON_NORMAL
    }

    public static EventsController getInstance() {
        if (instance == null) {
            instance = new EventsController();
        }
        return instance;
    }

    public EventsController() {
        this.events = new ArrayList<>();
        this.random = new Random();
        createEvents();
    }

    private void createEvents() {
        // NORMAL
        this.events.add(new Event(EventType.ZOMBIE_NORMAL, "Ataque Zombie", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SKELETON_NORMAL, "Ataque de Esqueletos", GameController.Category.NORMAL, 0));
    }

    public void handleNewEvent(Event event) {
        event.announce();
        if (event.getDuration() != 0) {
            GameController.getInstance().getActiveEvents().add(event);
        } else {
            applyEventInstantEffect(event);
        }
    }

    private void applyEventInstantEffect(Event event) {
        switch (event.getEventType()) {
            case ZOMBIE_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(5) + 1; ++i)
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
            });
            case SKELETON_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(5) + 1; ++i)
                    p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
            });
        }
    }

}
