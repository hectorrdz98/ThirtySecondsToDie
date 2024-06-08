package dev.sasukector.thirtysecondstodie.controllers;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.helpers.ServerUtilities;
import dev.sasukector.thirtysecondstodie.models.Event;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bed;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventsController {

    public static EventsController instance = null;
    private @Getter final List<Event> events;
    private @Getter @Setter Map<EventType, Integer> accEvents;
    private final Random random;

    public enum EventType {
        // Normal events
        MINI_ZOMBIES_NORMAL, ZOMBIE_NORMAL, SKELETON_NORMAL, CREEPER_NORMAL, SLIMES_NORMAL, ANVIL_NORMAL,
        COBWEB_NORMAL, EFFECT_NORMAL, DROP_HANDED_NORMAL, SKIP_DAY_NORMAL, RANDOM_TP_NORMAL,
        // Rare events
        EFFECT_RARE, BUNNIES_RARE, ILLUSIONER_RARE, SHUFFLE_RARE, HUNGER_RARE, CREEPER_RARE, NO_CHESTS_RARE,
        VINDICATOR_RARE, FIRE_RARE, PUFFER_FISH_RARE, TRAP_RARE, SAND_RARE, LIQUIDS_RARE,
        // Epic events
        UHC_MODE_EPIC, BAD_OMEN_EPIC, WITHER_SKELETONS_EPIC, VEX_EPIC, ENDERMAN_EPIC, VOID_EPIC,
        ANVIL_EPIC, WATER_DROP_EPIC, NO_MOVE_EPIC, FLOOR_LAVA_EPIC, DROWNED_EPIC, SNIPER_EPIC,
        SHADOUNE_EPIC, ONLY_BOW_EPIC, SILVER_FISH_EPIC,
        // Legendary events
        THUNDERS_LEG, INMORTAL_MOBS_LEG, GHAST_LEG, PHANTOM_LEG, SKELETON_LEG, TP_LEG, ADVENTURE_LEG,
        TOTEM_LEG, ARMOR_LEG, LV_ZOMBIES_LEG, LV_SKELETONS_LEG, LV_SPIDERS_LEG, BLOCKS_RANDOM_LEG,
        BLOCKS_EXPLODE_LEG,
        // God events
        SPAWN_DIVINE, HEAL_DRAGON_DIVINE, WITHERS_DIVINE, KNIGHT_DIVINE, CONTERSTINE_DIVINE;

        public static Character getIcon(EventType eventType) {
            Character character = null;
            switch (eventType) {
                case TOTEM_LEG -> character = ServerUtilities.getCharFromString("E006");
                case LV_ZOMBIES_LEG -> character = ServerUtilities.getCharFromString("E002");
                case LV_SKELETONS_LEG -> character = ServerUtilities.getCharFromString("E001");
                case LV_SPIDERS_LEG -> character = ServerUtilities.getCharFromString("E004");
                case BLOCKS_RANDOM_LEG -> character = ServerUtilities.getCharFromString("E003");
                case BLOCKS_EXPLODE_LEG -> character = ServerUtilities.getCharFromString("E005");
                default -> character = null;
            }
            return character;
        }
    }

    public static EventsController getInstance() {
        if (instance == null) {
            instance = new EventsController();
        }
        return instance;
    }

    public EventsController() {
        this.events = new ArrayList<>();
        this.accEvents = new HashMap<>();
        this.random = new Random();
        createEvents();
        createAccEvents();
    }

    private void createEvents() {
        // NORMAL
        this.events.add(new Event(EventType.MINI_ZOMBIES_NORMAL, "Fino señores", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.ZOMBIE_NORMAL, "Androides", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SKELETON_NORMAL, "Veganismo", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.CREEPER_NORMAL, "Ser explosivo", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SLIMES_NORMAL, "Gelatina que levita", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.ANVIL_NORMAL, "Headshot", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.COBWEB_NORMAL, "Mini trampa", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.EFFECT_NORMAL, "No me siento bien", GameController.Category.NORMAL, 0));
        this.events
                .add(new Event(EventType.DROP_HANDED_NORMAL, "Mano de mantequilla", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.SKIP_DAY_NORMAL, "Adios día...", GameController.Category.NORMAL, 0));
        this.events.add(new Event(EventType.RANDOM_TP_NORMAL, "¿Dónde estoy?", GameController.Category.NORMAL, 0));

        // RARE
        this.events
                .add(new Event(EventType.EFFECT_RARE, "Enserio... no me siento bien", GameController.Category.RARE, 0));
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
        this.events.add(
                new Event(EventType.LIQUIDS_RARE, "Juro que aquí había otra cosa", GameController.Category.RARE, 0));

        // EPIC
        this.events.add(new Event(EventType.UHC_MODE_EPIC, "Un UHC...", GameController.Category.EPIC, 180));
        this.events.add(new Event(EventType.BAD_OMEN_EPIC, "¿Pillagers? Hm...", GameController.Category.EPIC, 0));
        this.events
                .add(new Event(EventType.WITHER_SKELETONS_EPIC, "Almas del Nether", GameController.Category.EPIC, 0));
        this.events.add(new Event(EventType.VEX_EPIC, "Moscas", GameController.Category.EPIC, 0));
        this.events.add(new Event(EventType.ENDERMAN_EPIC, "Guardianes del bosque", GameController.Category.EPIC, 0));
        this.events.add(new Event(EventType.VOID_EPIC, "Una pequeña grieta", GameController.Category.EPIC, 0));
        this.events.add(new Event(EventType.ANVIL_EPIC, "Mucho hierro", GameController.Category.EPIC, 30));
        this.events.add(new Event(EventType.WATER_DROP_EPIC, "¡WaterDrop!", GameController.Category.EPIC, 0));
        this.events.add(new Event(EventType.NO_MOVE_EPIC, "Shh... No te muevas", GameController.Category.EPIC, 10));
        this.events.add(new Event(EventType.FLOOR_LAVA_EPIC, "¿Lava? ¿¡LAVA!?", GameController.Category.EPIC, 120));
        this.events.add(new Event(EventType.DROWNED_EPIC, "Finalmente, tridentes...", GameController.Category.EPIC, 0));
        this.events
                .add(new Event(EventType.SNIPER_EPIC, "No me dispares... por favor", GameController.Category.EPIC, 0));
        this.events
                .add(new Event(EventType.SHADOUNE_EPIC, "¿1/2 de vida? ¿Shadoune?", GameController.Category.EPIC, 0));
        this.events.add(new Event(EventType.ONLY_BOW_EPIC, "Solo sé usar el arco", GameController.Category.EPIC, 120));
        this.events
                .add(new Event(EventType.SILVER_FISH_EPIC, "Pequeños pero molestos", GameController.Category.EPIC, 0));

        // LEGENDARY
        this.events.add(new Event(EventType.THUNDERS_LEG, "¿El rasho?", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.INMORTAL_MOBS_LEG, "Uy... parece que no mueren",
                GameController.Category.LEGENDARY, 120));
        this.events.add(new Event(EventType.GHAST_LEG, "¿Bloons?", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.PHANTOM_LEG, "¿TNT? ¿Del cielo?", GameController.Category.LEGENDARY, 0));
        this.events
                .add(new Event(EventType.SKELETON_LEG, "Muchos... esqueletos", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.TP_LEG, "Los orígenes", GameController.Category.LEGENDARY, 0));
        this.events
                .add(new Event(EventType.ADVENTURE_LEG, "¿Aventura... No...?", GameController.Category.LEGENDARY, 180));
        this.events.add(new Event(EventType.ARMOR_LEG, "La mejor defensa", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.LV_ZOMBIES_LEG, "+ lv zombies", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.LV_SKELETONS_LEG, "+ lv esqueletos", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.LV_SPIDERS_LEG, "+ lv arañas", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.BLOCKS_RANDOM_LEG, "¿Qué bloque?", GameController.Category.LEGENDARY, 0));
        this.events.add(new Event(EventType.BLOCKS_EXPLODE_LEG, "¿Seguro que no era TNT?",
                GameController.Category.LEGENDARY, 0));

        // GOD
        this.events.add(new Event(EventType.SPAWN_DIVINE, "¿Y mi spawn?", GameController.Category.GOD, 0));
        this.events
                .add(new Event(EventType.HEAL_DRAGON_DIVINE, "Mucha vida al dragón", GameController.Category.GOD, 0));
        this.events.add(new Event(EventType.WITHERS_DIVINE, "¿Withers?", GameController.Category.GOD, 0));
        this.events.add(new Event(EventType.KNIGHT_DIVINE, "El héroe del Nether", GameController.Category.GOD, 0));
        this.events.add(new Event(EventType.CONTERSTINE_DIVINE, "Conterstine...", GameController.Category.GOD, 0));
    }

    public void createAccEvents() {
        this.accEvents.put(EventType.TOTEM_LEG, 0);
        this.accEvents.put(EventType.LV_ZOMBIES_LEG, 0);
        this.accEvents.put(EventType.LV_SKELETONS_LEG, 0);
        this.accEvents.put(EventType.LV_SPIDERS_LEG, 0);
        this.accEvents.put(EventType.BLOCKS_RANDOM_LEG, 0);
        this.accEvents.put(EventType.BLOCKS_EXPLODE_LEG, 0);
    }

    public void handleNewEvent(Event event) {
        event.announce();
        if (event.getDuration() != 0) {
            event.setMissingTime(event.getDuration());
            if (!GameController.getInstance().getActiveEvents().contains(event)) {
                GameController.getInstance().getActiveEvents().add(event);
            }
        } else {
            applyEventInstantEffect(event);
        }
    }

    public void timedEventController() {
        List<Event> events = GameController.getInstance().getActiveEvents().stream().toList();
        for (Event event : events) {
            event.setMissingTime(event.getMissingTime() - 1);
            if (event.getMissingTime() < 0) {
                GameController.getInstance().getActiveEvents().remove(event);
            }
            applyEventTimedEffect(event);
        }
    }

    private void applyEventInstantEffect(Event event) {
        switch (event.getEventType()) {
            case MINI_ZOMBIES_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 2; ++i) {
                    Zombie miniZombie = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                    miniZombie.customName(Component.text("Zombie Facha", TextColor.color(0xDF7CFF)));
                    miniZombie.setBaby();
                    Objects.requireNonNull(miniZombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
                            .setBaseValue(0.3f);
                    miniZombie.setTarget(p);
                }
            });
            case ZOMBIE_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 1; ++i) {
                    Zombie zombie = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                    zombie.customName(Component.text("XR-Zombie", TextColor.color(0x6BD3FF)));
                    Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
                            .setBaseValue(0.4f);
                    Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(30);
                    zombie.setHealth(30);
                    zombie.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));
                    zombie.setTarget(p);
                }
            });
            case SKELETON_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 1; ++i) {
                    Skeleton skeleton = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
                    skeleton.customName(Component.text("Esqueleto Vegano", TextColor.color(0x37FF56)));
                    Objects.requireNonNull(skeleton.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
                            .setBaseValue(0.4f);
                    Objects.requireNonNull(skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(30);
                    skeleton.setHealth(30);
                    skeleton.setTarget(p);
                }
            });
            case CREEPER_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                Creeper creeper = (Creeper) p.getWorld().spawnEntity(p.getLocation(), EntityType.CREEPER);
                creeper.customName(Component.text("Creeper Suicida", TextColor.color(0xFF605B)));
                Objects.requireNonNull(creeper.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(30);
                creeper.setHealth(30);
                creeper.setTarget(p);
            });
            case SLIMES_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 2; ++i) {
                    Slime slime = (Slime) p.getWorld().spawnEntity(p.getLocation(), EntityType.SLIME);
                    slime.getScoreboardTags().add("custom_slime");
                    slime.customName(Component.text("Espiritu Gelatinoso", TextColor.color(0xA0F0FF)));
                    slime.setTarget(p);
                }
            });
            case ANVIL_NORMAL -> Bukkit.getOnlinePlayers()
                    .forEach(p -> p.getWorld().getBlockAt(p.getLocation().add(0, 6, 0)).setType(Material.ANVIL));
            case COBWEB_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                List<int[]> locations = Arrays.asList(
                        new int[] { 0, 0 }, new int[] { 1, 0 }, new int[] { 0, 1 },
                        new int[] { -1, 0 }, new int[] { 0, -1 }, new int[] { 1, 1 },
                        new int[] { 1, -1 }, new int[] { -1, 1 }, new int[] { -1, -1 });
                for (int[] l : locations) {
                    Block block = p.getWorld().getBlockAt(p.getLocation().add(l[0], -1, l[1]));
                    if (block.getType() != Material.END_PORTAL_FRAME && block.getType() != Material.BEDROCK) {
                        block.setType(Material.COBWEB);
                    }
                }
            });
            case EFFECT_NORMAL -> Bukkit.getOnlinePlayers().forEach(p -> {
                switch (random.nextInt(6)) {
                    case 0 -> p.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 600, 1));
                    case 1 -> p.addPotionEffect(new PotionEffect(PotionEffectType.MINING_FATIGUE, 600, 1));
                    case 2 -> p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 600, 1));
                    case 3 -> p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 600, 1));
                    case 4 -> p.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 600, 1));
                    default -> p.addPotionEffect(new PotionEffect(PotionEffectType.NAUSEA, 600, 1));
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
                newLocation.add(random.nextInt(200) - 100, 0, random.nextInt(200) - 100);
                ServerUtilities.teleportPlayerToSafeHeight(p, newLocation);
            });
            case EFFECT_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                switch (random.nextInt(3)) {
                    case 0 -> p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 600, 0));
                    case 1 -> p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 600, 1));
                    default -> p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 600, 2));
                }
            });
            case BUNNIES_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < random.nextInt(3) + 3; ++i) {
                    Rabbit rabbit = (Rabbit) p.getWorld().spawnEntity(p.getLocation(), EntityType.RABBIT);
                    rabbit.setRabbitType(Rabbit.Type.THE_KILLER_BUNNY);
                    rabbit.customName(Component.text("Conejito chocolatoso", TextColor.color(0x9E513B)));
                    rabbit.setTarget(p);
                }
            });
            case ILLUSIONER_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                Illusioner illusioner = (Illusioner) p.getWorld().spawnEntity(p.getLocation(), EntityType.ILLUSIONER);
                illusioner.setTarget(p);
            });
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
                Objects.requireNonNull(creeper.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.5f);
                creeper.setTarget(p);
            });
            case VINDICATOR_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                Vindicator vindicator = (Vindicator) p.getWorld().spawnEntity(p.getLocation(), EntityType.VINDICATOR);
                vindicator.customName(Component.text("Casi Humano", TextColor.color(0xA4A84A)));
                vindicator.setTarget(p);
            });
            case TRAP_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                List<int[]> glassLoc = Arrays.asList(
                        new int[] { 1, 0, 0 }, new int[] { -1, 0, 0 }, new int[] { 0, 0, 1 }, new int[] { 0, 0, -1 },
                        new int[] { 1, 1, 0 }, new int[] { -1, 1, 0 }, new int[] { 0, 1, 1 }, new int[] { 0, 1, -1 },
                        new int[] { 0, 2, 0 }, new int[] { 0, -1, 0 });
                for (int[] l : glassLoc) {
                    Block block = p.getWorld().getBlockAt(p.getLocation().add(l[0], l[1], l[2]));
                    if (block.getType() != Material.END_PORTAL_FRAME && block.getType() != Material.BEDROCK) {
                        block.setType(Material.GLASS);
                    }
                }
                Block block = p.getWorld().getBlockAt(p.getLocation());
                if (block.getType() != Material.END_PORTAL_FRAME && block.getType() != Material.BEDROCK) {
                    block.setType(Material.WATER);
                }
                block = p.getWorld().getBlockAt(p.getLocation().add(0, 1, 0));
                if (block.getType() != Material.END_PORTAL_FRAME && block.getType() != Material.BEDROCK) {
                    block.setType(Material.WATER);
                }

            });
            case SAND_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 5; ++i) {
                    for (int j = -i; j <= i; ++j) {
                        for (int k = -i; k <= i; ++k) {
                            Block block = p.getWorld().getBlockAt(p.getLocation().add(j, 5 + i, k));
                            if (block.getType() != Material.END_PORTAL_FRAME && block.getType() != Material.BEDROCK) {
                                block.setType(Material.SAND);
                            }
                        }
                    }
                }
            });
            case LIQUIDS_RARE -> Bukkit.getOnlinePlayers().forEach(p -> {
                int baseX = p.getLocation().getBlockX();
                int baseY = p.getLocation().getBlockY();
                int baseZ = p.getLocation().getBlockZ();
                for (int i = baseX - 20; i <= baseX + 20; ++i) {
                    for (int j = baseY - 5; j <= baseY + 5; ++j) {
                        for (int k = baseZ - 20; k <= baseZ + 20; ++k) {
                            Block block = p.getWorld().getBlockAt(i, j, k);
                            if (block.getType() == Material.WATER) {
                                block.setType(Material.LAVA);
                            } else if (block.getType() == Material.LAVA) {
                                block.setType(Material.WATER);
                            }
                        }
                    }
                }
            });
            case BAD_OMEN_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                Block bedHead = p.getLocation().add(0, 10, 0).getBlock();
                Block bedFoot = p.getLocation().add(1, 10, 0).getBlock();
                BlockFace facing = bedHead.getFace(bedFoot);
                if (facing != null) {
                    for (Bed.Part part : Bed.Part.values()) {
                        if (bedHead.getType() != Material.END_PORTAL_FRAME && bedHead.getType() != Material.BEDROCK) {
                            bedHead.setBlockData(Bukkit.createBlockData(Material.WHITE_BED, (data) -> {
                                ((Bed) data).setPart(part);
                                ((Bed) data).setFacing(facing);
                            }));
                        }
                        bedHead = bedHead.getRelative(facing.getOppositeFace());
                    }
                }
                p.getWorld().spawnEntity(p.getLocation().add(0, 11, 0), EntityType.VILLAGER);
                p.getWorld().spawnEntity(p.getLocation().add(0, 11, 0), EntityType.VILLAGER);
                p.addPotionEffect(new PotionEffect(PotionEffectType.BAD_OMEN, 99999, 4));
            });
            case WITHER_SKELETONS_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 3; ++i) {
                    WitherSkeleton witherSkeleton = (WitherSkeleton) p.getWorld().spawnEntity(p.getLocation(),
                            EntityType.WITHER_SKELETON);
                    witherSkeleton.customName(Component.text("Oscuridad Total", TextColor.color(0x676867)));
                    Objects.requireNonNull(witherSkeleton.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
                            .setBaseValue(0.4f);
                    Objects.requireNonNull(witherSkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(60);
                    witherSkeleton.setHealth(60);
                    witherSkeleton.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));
                    witherSkeleton.setTarget(p);
                }
            });
            case VEX_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 2; ++i) {
                    Vex vex = (Vex) p.getWorld().spawnEntity(p.getLocation(), EntityType.VEX);
                    vex.customName(Component.text("Negocio Serio", TextColor.color(0x4A4B4A)));
                    vex.setTarget(p);
                }
            });
            case ENDERMAN_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 2; ++i) {
                    Enderman enderman = (Enderman) p.getWorld().spawnEntity(p.getLocation(), EntityType.ENDERMAN);
                    enderman.customName(Component.text("Terror Andante", TextColor.color(0xB85F42)));
                    Objects.requireNonNull(enderman.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(60);
                    enderman.setHealth(60);
                    enderman.setTarget(p);
                }
            });
            case VOID_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                Location location = p.getLocation();
                for (int i = location.getBlockY(); i >= 0; --i) {
                    location.setY(i);
                    Block block = p.getWorld().getBlockAt(location);
                    if (block.getType() != Material.END_PORTAL_FRAME) {
                        block.setType(Material.AIR);
                    }
                }
            });
            case WATER_DROP_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                p.getInventory().addItem(new ItemStack(Material.WATER_BUCKET));
                p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 60, 99));
            });
            case DROWNED_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 5; ++i) {
                    Drowned drowned = (Drowned) p.getWorld().spawnEntity(p.getLocation(), EntityType.DROWNED);
                    drowned.getScoreboardTags().add("not_upgradable");
                    drowned.getEquipment().setItemInMainHand(new ItemStack(Material.TRIDENT));
                    Objects.requireNonNull(drowned.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(30);
                    drowned.setHealth(30);
                    drowned.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));
                    drowned.setTarget(p);
                }
            });
            case SNIPER_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 4; ++i) {
                    Skeleton skeleton = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
                    skeleton.getScoreboardTags().add("not_upgradable");
                    skeleton.customName(Component.text("Francotirador", TextColor.color(0x4066B8)));
                    ItemStack bow = new ItemStack(Material.BOW);
                    ItemMeta itemMeta = bow.getItemMeta();
                    itemMeta.addEnchant(Enchantment.KNOCKBACK, 20, true);
                    itemMeta.addEnchant(Enchantment.POWER, 5, true);
                    bow.setItemMeta(itemMeta);
                    skeleton.getEquipment().setItemInMainHand(bow);
                    Objects.requireNonNull(skeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(40);
                    skeleton.setHealth(40);
                    skeleton.setTarget(p);
                }
            });
            case SHADOUNE_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> p.setHealth(0.5));
            case SILVER_FISH_EPIC -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 10; ++i) {
                    Silverfish silverfish = (Silverfish) p.getWorld().spawnEntity(p.getLocation(),
                            EntityType.SILVERFISH);
                    silverfish.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, 1));
                    silverfish.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
                    silverfish.setTarget(p);
                }
            });
            case THUNDERS_LEG -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 5; ++i) {
                    Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(),
                            () -> p.getWorld().strikeLightning(p.getLocation()), 20L * i);
                }
            });
            case GHAST_LEG -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 2; ++i) {
                    Ghast ghast = (Ghast) p.getWorld().spawnEntity(p.getLocation(), EntityType.GHAST);
                    ghast.getScoreboardTags().add("custom_ghast");
                    ghast.customName(Component.text("Bloon Calabaza", TextColor.color(0xB86141)));
                    Objects.requireNonNull(ghast.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(40);
                    ghast.setHealth(40);
                    ghast.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));
                    ghast.setTarget(p);
                }
            });
            case PHANTOM_LEG -> Bukkit.getOnlinePlayers().forEach(p -> {
                Phantom phantom = (Phantom) p.getWorld().spawnEntity(p.getLocation().add(0, 20, 0), EntityType.PHANTOM);
                phantom.getScoreboardTags().add("custom_phantom");
                phantom.setPersistent(true);
                phantom.customName(Component.text("Espiritu Rojo", TextColor.color(0xA61A2A)));
                phantom.setSize(30);
                Objects.requireNonNull(phantom.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100);
                phantom.setHealth(100);
                phantom.setTarget(p);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (phantom.isDead()) {
                            cancel();
                        } else {
                            phantom.getWorld().spawnEntity(phantom.getLocation().add(0, -1, 0), EntityType.TNT);
                        }
                    }
                }.runTaskTimer(ThirtySecondsToDie.getInstance(), 0L, 20L);
            });
            case SKELETON_LEG -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 50; ++i) {
                    Skeleton skeleton = (Skeleton) p.getWorld().spawnEntity(p.getLocation(), EntityType.SKELETON);
                    skeleton.getScoreboardTags().add("custom_skeleton");
                    skeleton.customName(Component.text("Esqueleto con Camisa", TextColor.color(0xA67493)));
                    skeleton.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));
                    skeleton.getEquipment().setItemInMainHand(null);
                    skeleton.getEquipment().setHelmet(null);
                    skeleton.getEquipment().setChestplate(null);
                    skeleton.getEquipment().setLeggings(null);
                    skeleton.getEquipment().setBoots(null);
                    skeleton.setCanPickupItems(false);
                }
            });
            case TP_LEG -> Bukkit.getOnlinePlayers().forEach(p -> {
                p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING, 300, 0));
                ServerUtilities.teleportPlayerToSafeHeight(p, new Location(ServerUtilities.getOverworld(), 0, 70, 0));
            });
            case TOTEM_LEG -> {
                int currentValue = this.accEvents.get(EventType.TOTEM_LEG);
                this.accEvents.put(EventType.TOTEM_LEG, currentValue + 1);
                ServerUtilities.sendBroadcastMessage(ServerUtilities
                        .getCategoryColor(GameController.getInstance().getCurrentCategory()) +
                        "Porcentaje actual " + (currentValue + 1) + "%");
            }
            case ARMOR_LEG -> Bukkit.getOnlinePlayers().forEach(p -> {
                ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                helmet.addEnchantment(Enchantment.BINDING_CURSE, 1);
                p.getInventory().setHelmet(helmet);
                ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                chestplate.addEnchantment(Enchantment.BINDING_CURSE, 1);
                p.getInventory().setChestplate(chestplate);
                ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                leggings.addEnchantment(Enchantment.BINDING_CURSE, 1);
                p.getInventory().setLeggings(leggings);
                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                boots.addEnchantment(Enchantment.BINDING_CURSE, 1);
                p.getInventory().setBoots(boots);
            });
            case LV_ZOMBIES_LEG -> {
                int currentValue = this.accEvents.get(EventType.LV_ZOMBIES_LEG);
                this.accEvents.put(EventType.LV_ZOMBIES_LEG, currentValue + 1);
                ServerUtilities.sendBroadcastMessage(ServerUtilities
                        .getCategoryColor(GameController.getInstance().getCurrentCategory()) +
                        "Nivel actual " + (currentValue + 1));
            }
            case LV_SKELETONS_LEG -> {
                int currentValue = this.accEvents.get(EventType.LV_SKELETONS_LEG);
                this.accEvents.put(EventType.LV_SKELETONS_LEG, currentValue + 1);
                ServerUtilities.sendBroadcastMessage(ServerUtilities
                        .getCategoryColor(GameController.getInstance().getCurrentCategory()) +
                        "Nivel actual " + (currentValue + 1));
            }
            case LV_SPIDERS_LEG -> {
                int currentValue = this.accEvents.get(EventType.LV_SPIDERS_LEG);
                this.accEvents.put(EventType.LV_SPIDERS_LEG, currentValue + 1);
                ServerUtilities.sendBroadcastMessage(ServerUtilities
                        .getCategoryColor(GameController.getInstance().getCurrentCategory()) +
                        "Nivel actual " + (currentValue + 1));
            }
            case BLOCKS_RANDOM_LEG -> {
                int currentValue = this.accEvents.get(EventType.BLOCKS_RANDOM_LEG);
                this.accEvents.put(EventType.BLOCKS_RANDOM_LEG, currentValue + 10);
                ServerUtilities.sendBroadcastMessage(ServerUtilities
                        .getCategoryColor(GameController.getInstance().getCurrentCategory()) +
                        "Porcentaje actual " + (currentValue + 10) + "%");
            }
            case BLOCKS_EXPLODE_LEG -> {
                int currentValue = this.accEvents.get(EventType.BLOCKS_EXPLODE_LEG);
                this.accEvents.put(EventType.BLOCKS_EXPLODE_LEG, currentValue + 5);
                ServerUtilities.sendBroadcastMessage(ServerUtilities
                        .getCategoryColor(GameController.getInstance().getCurrentCategory()) +
                        "Porcentaje actual " + (currentValue + 5) + "%");
            }
            case SPAWN_DIVINE -> Bukkit.getOnlinePlayers()
                    .forEach(p -> p.setRespawnLocation(new Location(ServerUtilities.getOverworld(), 0, 70, 0), true));
            case HEAL_DRAGON_DIVINE -> Bukkit.getOnlinePlayers().forEach(p -> {
                EnderDragon enderDragon = GameController.getInstance().getEnderDragon();
                if (enderDragon != null) {
                    enderDragon.setHealth(500);
                }
            });
            case WITHERS_DIVINE -> Bukkit.getOnlinePlayers().forEach(p -> {
                for (int i = 0; i < 3; ++i) {
                    Wither wither = (Wither) p.getWorld().spawnEntity(p.getLocation(), EntityType.WITHER);
                    wither.customName(Component.text("Terror Encarnado", TextColor.color(0x4A4B4A)));
                    wither.setTarget(p);
                }
            });
            case KNIGHT_DIVINE -> Bukkit.getOnlinePlayers().forEach(p -> {
                WitherSkeleton witherSkeleton = (WitherSkeleton) p.getWorld().spawnEntity(p.getLocation(),
                        EntityType.WITHER_SKELETON);
                witherSkeleton.getScoreboardTags().add("not_upgradable");
                witherSkeleton.customName(Component.text("Oscuridad Total", TextColor.color(0x4A4B4A)));
                Objects.requireNonNull(witherSkeleton.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100);
                Objects.requireNonNull(witherSkeleton.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED))
                        .setBaseValue(0.5f);
                witherSkeleton.setHealth(100);
                witherSkeleton.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));

                EntityEquipment entityEquipment = witherSkeleton.getEquipment();

                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                helmet.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                entityEquipment.setHelmet(helmet);

                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                entityEquipment.setChestplate(chestplate);

                ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                leggings.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                entityEquipment.setLeggings(leggings);

                ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                boots.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                entityEquipment.setBoots(boots);

                ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                sword.addUnsafeEnchantment(Enchantment.SHARPNESS, 7);
                entityEquipment.setItemInMainHand(sword);
            });
            case CONTERSTINE_DIVINE -> Bukkit.getOnlinePlayers().forEach(p -> {
                Zombie zombie = (Zombie) p.getWorld().spawnEntity(p.getLocation(), EntityType.ZOMBIE);
                zombie.getScoreboardTags().add("not_upgradable");
                zombie.customName(Component.text("Conterstine", TextColor.color(0x37FF56)));
                Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(0.4f);
                Objects.requireNonNull(zombie.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(100);
                zombie.setHealth(100);
                zombie.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 99999, 9, true, false));

                EntityEquipment entityEquipment = zombie.getEquipment();

                ItemStack helmet = new ItemStack(Material.NETHERITE_HELMET);
                helmet.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                helmet.addUnsafeEnchantment(Enchantment.RESPIRATION, 3);
                entityEquipment.setHelmet(helmet);

                ItemStack chestplate = new ItemStack(Material.NETHERITE_CHESTPLATE);
                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                entityEquipment.setChestplate(chestplate);

                ItemStack leggings = new ItemStack(Material.NETHERITE_LEGGINGS);
                leggings.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                entityEquipment.setLeggings(leggings);

                ItemStack boots = new ItemStack(Material.NETHERITE_BOOTS);
                boots.addUnsafeEnchantment(Enchantment.PROTECTION, 4);
                boots.addUnsafeEnchantment(Enchantment.SOUL_SPEED, 3);
                boots.addUnsafeEnchantment(Enchantment.DEPTH_STRIDER, 5);
                entityEquipment.setBoots(boots);

                ItemStack sword = new ItemStack(Material.NETHERITE_SWORD);
                sword.addUnsafeEnchantment(Enchantment.SHARPNESS, 5);
                sword.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 1);
                sword.addUnsafeEnchantment(Enchantment.SWEEPING_EDGE, 3);
                entityEquipment.setItemInMainHand(sword);

                ItemStack shield = new ItemStack(Material.SHIELD);
                shield.addUnsafeEnchantment(Enchantment.UNBREAKING, 3);
                entityEquipment.setItemInOffHand(shield);
            });
            default -> {
            }
        }
    }

    private void applyEventTimedEffect(Event event) {
        switch (event.getEventType()) {
            case FIRE_RARE -> Bukkit.getOnlinePlayers().forEach(p -> p.setFireTicks(100));
            case PUFFER_FISH_RARE -> Bukkit.getOnlinePlayers()
                    .forEach(p -> Bukkit.getScheduler().runTask(ThirtySecondsToDie.getInstance(), () -> {
                        for (int i = 0; i < random.nextInt(3) + 1; ++i) {
                            p.getWorld().spawnEntity(p.getLocation()
                                    .add(random.nextInt(3), 10, random.nextInt(3)), EntityType.PUFFERFISH);
                        }
                    }));
            case ANVIL_EPIC -> Bukkit.getOnlinePlayers()
                    .forEach(p -> Bukkit.getScheduler().runTask(ThirtySecondsToDie.getInstance(), () -> {
                        for (int i = 0; i < random.nextInt(3) + 1; ++i) {
                            p.getWorld().getBlockAt(p.getLocation().add(random.nextInt(3), 6, random.nextInt(3)))
                                    .setType(Material.ANVIL);
                        }
                    }));
            default -> {
            }
        }
    }

}
