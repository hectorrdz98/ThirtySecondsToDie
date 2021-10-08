package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.models.Event;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class EventsController {

    public static EventsController instance = null;
    private @Getter final List<Event> events;
    private final Random random;

    public enum EventType {
        MINI_ZOMBIES_NORMAL, ZOMBIE_NORMAL, SKELETON_NORMAL, CREEPER_NORMAL,
        SLIMES_NORMAL, ANVIL_NORMAL, COBWEB_NORMAL, EFFECT_NORMAL
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
        this.events.add(new Event(EventType.MINI_ZOMBIES_NORMAL, "Los Blancos", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.ZOMBIE_NORMAL, "XR-Zombies", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SKELETON_NORMAL, "Veganismo", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.CREEPER_NORMAL, "Creeper Suicida", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SLIMES_NORMAL, "Espíritu Gelatinoso"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.ANVIL_NORMAL, "Headshot"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.COBWEB_NORMAL, "Mini Trampa"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.EFFECT_NORMAL, "Efecto Básico Random"  , GameController.Category.NORMAL, 0));
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
            case MINI_ZOMBIES_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 3; ++i) {
                    Zombie miniZombie = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                    miniZombie.customName(Component.text("Zombie Facha", TextColor.color(0xDF7CFF)));
                    miniZombie.setBaby();
                    Objects.requireNonNull(miniZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.3f);
                }
            });
            case ZOMBIE_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 3; ++i) {
                    Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                    entity.customName(Component.text("XR-Zombie", TextColor.color(0x6BD3FF)));
                }

            });
            case SKELETON_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 3; ++i) {
                    Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
                    entity.customName(Component.text("Esqueleto Vegano", TextColor.color(0x37FF56)));
                }
            });
            case CREEPER_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
                entity.customName(Component.text("Creeper Suicida", TextColor.color(0xFF605B)));
            });
            case SLIMES_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 2; ++i) {
                    Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.SLIME);
                    entity.getScoreboardTags().add("custom_slime");
                    entity.customName(Component.text("Espiritu Gelatinoso", TextColor.color(0xA0F0FF)));
                }
            });
            case ANVIL_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> p.getWorld().spawnFallingBlock(p.getLocation().add(0, 10, 0), Material.ANVIL.createBlockData()));
            case COBWEB_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                p.getWorld().getBlockAt(p.getLocation().add(0, -1, 0)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(1, -1, 0)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(0, -1, 1)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(-1, -1, 0)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(0, -1, -1)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(1, -1, 1)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(1, -1, -1)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(-1, -1, 1)).setType(Material.COBWEB);
                p.getWorld().getBlockAt(p.getLocation().add(-1, -1, -1)).setType(Material.COBWEB);
            });
            case EFFECT_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                switch (random.nextInt(5)) {
                    case 0 -> p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 600, 1));
                    case 1 -> p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, 600, 0));
                    case 2 -> p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 0));
                    case 3 -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 0));
                    default -> p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 600, 0));
                }
            });
        }
    }

}
