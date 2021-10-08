package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.models.Event;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventsController {

    public static EventsController instance = null;
    private @Getter final List<Event> events;
    private final Random random;

    public enum EventType {
        // Normal events
        MINI_ZOMBIES_NORMAL, ZOMBIE_NORMAL, SKELETON_NORMAL, CREEPER_NORMAL, SLIMES_NORMAL, ANVIL_NORMAL,
        COBWEB_NORMAL, EFFECT_NORMAL, DROP_HANDED_NORMAL, SKIP_DAY_NORMAL, RANDOM_TP_NORMAL,
        // Rare events
        EFFECT_RARE, BUNNIES_RARE, ILLUSIONER_RARE, SHUFFLE_RARE, HUNGER_RARE, CREEPER_RARE, NO_CHESTS_RARE,
        VINDICATOR_RARE, FIRE_RARE, PUFFER_FISH_RARE, TRAP_RARE, SAND_RARE, LIQUIDS_RARE, DURABILITY_RARE
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
        this.events.add(new Event(EventType.MINI_ZOMBIES_NORMAL, "Fino señores", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.ZOMBIE_NORMAL, "Androides", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SKELETON_NORMAL, "Veganismo", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.CREEPER_NORMAL, "Ser explosivo", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SLIMES_NORMAL, "Gelatina que levita"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.ANVIL_NORMAL, "Headshot"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.COBWEB_NORMAL, "Mini trampa"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.EFFECT_NORMAL, "No me siento bien"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.DROP_HANDED_NORMAL, "Mano de mantequilla"  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SKIP_DAY_NORMAL, "Adios día..."  , GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.RANDOM_TP_NORMAL, "¿Dónde estoy?"  , GameController.Category.NORMAL, 0));

        // RARE
        this.events.add(new Event(EventType.EFFECT_RARE, "Enserio... no me siento bien", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.BUNNIES_RARE, "¿Co-conter?", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.ILLUSIONER_RARE, "¿Un mago?", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.SHUFFLE_RARE, "¿Y mis cosas?", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.HUNGER_RARE, "Tengo hambre...", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.CREEPER_RARE, "¡EXPLOSIÓN!", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.NO_CHESTS_RARE, "¿Y la llave?", GameController.Category.RARE, 60));
        this.events.add(new Event(EventType.VINDICATOR_RARE, "Aldeano enojado", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.FIRE_RARE, "Hace calor", GameController.Category.RARE, 30));
        this.events.add(new Event(EventType.PUFFER_FISH_RARE, "Se ven esponjosos", GameController.Category.RARE, 30));
        this.events.add(new Event(EventType.TRAP_RARE, "¿Sin salida?", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.SAND_RARE, "¿Lag?", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.LIQUIDS_RARE, "Juro que aquí había otra cosa", GameController.Category.RARE, 0));
        this.events.add(new Event(EventType.DURABILITY_RARE, "Se gastan rápido", GameController.Category.RARE, 0));
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
                for (int i = 0; i < 2; ++i) {
                    Zombie miniZombie = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                    miniZombie.customName(Component.text("Zombie Facha", TextColor.color(0xDF7CFF)));
                    miniZombie.setBaby();
                    Objects.requireNonNull(miniZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.3f);
                }
            });
            case ZOMBIE_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 1; ++i) {
                    Entity entity = p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                    entity.customName(Component.text("XR-Zombie", TextColor.color(0x6BD3FF)));
                }

            });
            case SKELETON_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 1; ++i) {
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
                List<int[]> locations = Arrays.asList(
                        new int[]{0, 0}, new int[]{1, 0}, new int[]{0, 1},
                        new int[]{-1, 0}, new int[]{0, -1}, new int[]{1, 1},
                        new int[]{1, -1}, new int[]{-1, 1}, new int[]{-1, -1}
                );
                for (int[] l : locations) {
                    Block block = p.getWorld().getBlockAt(p.getLocation().add(l[0], -1, l[1]));
                    if (block.getType() != Material.END_PORTAL_FRAME && block.getType() != Material.BEDROCK) {
                        block.setType(Material.COBWEB);
                    }
                }
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
            case DROP_HANDED_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                ItemStack item = p.getInventory().getItemInMainHand();
                if (item.getType() != Material.AIR) {
                    p.getWorld().dropItemNaturally(p.getLocation(), item);
                    p.getInventory().setItemInMainHand(null);
                    p.updateInventory();
                }
            });
            case SKIP_DAY_NORMAL -> Bukkit.getWorlds().forEach(w -> w.setTime(18000));
            case RANDOM_TP_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                Location newLocation = p.getLocation();
                newLocation.add(random.nextInt(20) - 10, 0, random.nextInt(20) - 10);
                List<Integer> locations = Stream.iterate(2, n -> n + 1).limit(100).collect(Collectors.toList());
                Collections.shuffle(locations);
                for (int y : locations) {
                    newLocation.setY(y);
                    Block cBlock = newLocation.getBlock();
                    Block tBlock = newLocation.add(0, 1, 0).getBlock();
                    Block lBlock = newLocation.add(0, -2, 0).getBlock();
                    if ((cBlock.getType() == Material.AIR || cBlock.getType() == Material.WATER) &&
                            (tBlock.getType() == Material.AIR || tBlock.getType() == Material.WATER) &&
                            (lBlock.getType().isSolid() || lBlock.getType() == Material.WATER)
                    ) {
                        newLocation.setY(y);
                        p.teleport(newLocation);
                        break;
                    }
                }
            });
            case EFFECT_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                switch (random.nextInt(3)) {
                    case 0 -> p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 600, 0));
                    case 1 -> p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 0));
                    default -> p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 0));
                }
            });
            case BUNNIES_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 3; ++i) {
                    Rabbit rabbit = (Rabbit) p.getWorld().spawnEntity(p.getLocation(), EntityType.RABBIT);
                    rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
                    rabbit.customName(Component.text("Conejito chocolatoso", TextColor.color(0x9E513B)));
                }
            });
            case ILLUSIONER_RARE -> Bukkit.getOnlinePlayers().forEach(p -> p.getWorld().spawnEntity(p.getLocation(), EntityType.ILLUSIONER));
            case SHUFFLE_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                ItemStack[] items = p.getInventory().getContents();
                List<Integer> indexes = Stream.iterate(0, n -> n + 1).limit(items.length).collect(Collectors.toList());
                Collections.shuffle(indexes);
                for (int i = 0; i < items.length; ++i) {
                    p.getInventory().setItem(i, items[indexes.get(0)]);
                    indexes.remove(0);
                }
            });
            case HUNGER_RARE -> Bukkit.getOnlinePlayers().forEach(p -> p.setFoodLevel(0));
            case CREEPER_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
                creeper.customName(Component.text("Creeper Atomico", TextColor.color(0x2A854A)));
                creeper.setPowered(true);
                Objects.requireNonNull(creeper.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.4f);
            });
        }
    }

}
