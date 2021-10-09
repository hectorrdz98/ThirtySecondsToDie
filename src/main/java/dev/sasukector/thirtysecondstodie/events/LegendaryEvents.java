package dev.sasukector.thirtysecondstodie.events;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import dev.sasukector.thirtysecondstodie.controllers.GameController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class LegendaryEvents implements Listener {

    private final Random random = new Random();

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Ghast ghast) {
            if (ghast.getScoreboardTags().contains("custom_ghast")) {
                Fireball fireball = (Fireball) event.getEntity();
                Location location = fireball.getLocation();
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () -> {
                    Fireball fireball1 = (Fireball) fireball.getWorld().spawnEntity(location, EntityType.FIREBALL);
                    fireball1.setDirection(fireball.getDirection());
                }, 10L);
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () -> {
                    Fireball fireball1 = (Fireball) fireball.getWorld().spawnEntity(location, EntityType.FIREBALL);
                    fireball1.setDirection(fireball.getDirection());
                }, 20L);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Phantom phantom && phantom.getScoreboardTags().contains("custom_phantom") &&
                (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                        event.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                        event.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION ||
                        event.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
            event.setCancelled(true);
            phantom.setVisualFire(false);
            phantom.setFireTicks(0);
        }
        if (event.getEntity() instanceof Skeleton skeleton && skeleton.getScoreboardTags().contains("custom_skeleton") &&
                (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK ||
                        event.getCause() == EntityDamageEvent.DamageCause.FIRE)) {
            event.setCancelled(true);
            skeleton.setVisualFire(false);
            skeleton.setFireTicks(0);
        }
        if (event.getEntity() instanceof LivingEntity entity && entity.getType() != EntityType.PLAYER) {
            if (GameController.getInstance().getActiveEvents().stream()
                    .anyMatch(e -> e.getEventType() == EventsController.EventType.INMORTAL_MOBS_LEG)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (GameController.getInstance().getActiveEvents().stream()
                .anyMatch(e -> e.getEventType() == EventsController.EventType.ADVENTURE_LEG)) {
            event.setCancelled(true);
        } else {
            int lvlRandom = EventsController.getInstance().getAccEvents().get(EventsController.EventType.BLOCKS_RANDOM_LEG);
            if (random.nextDouble() < (lvlRandom / 100f)) {
                event.setCancelled(true);
                Block block = event.getBlock();
                ItemStack itemInHand = event.getPlayer().getInventory().getItemInMainHand();
                while (true) {
                    int randomX = random.nextInt(10) - 5;
                    int randomY = random.nextInt(10) - 5;
                    int randomZ = random.nextInt(10) - 5;
                    Block newBlock = block.getLocation().add(randomX, randomY, randomZ).getBlock();
                    if (!newBlock.isEmpty()) {
                        newBlock.breakNaturally(itemInHand);
                        break;
                    }
                }
            } else {
                int lvlExplode = EventsController.getInstance().getAccEvents().get(EventsController.EventType.BLOCKS_EXPLODE_LEG);
                if (random.nextDouble() < (lvlExplode / 100f)) {
                    Block block = event.getBlock();
                    block.getWorld().createExplosion(block.getLocation(), 3);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (GameController.getInstance().getActiveEvents().stream()
                .anyMatch(e -> e.getEventType() == EventsController.EventType.ADVENTURE_LEG)) {
            event.setCancelled(true);
        } else {
            int lvlRandom = EventsController.getInstance().getAccEvents().get(EventsController.EventType.BLOCKS_RANDOM_LEG);
            if (random.nextDouble() < (lvlRandom / 100f)) {
                event.setCancelled(true);
                Block block = event.getBlock();
                while (true) {
                    int randomX = random.nextInt(10) - 5;
                    int randomY = random.nextInt(10) - 5;
                    int randomZ = random.nextInt(10) - 5;
                    Block newBlock = block.getLocation().add(randomX, randomY, randomZ).getBlock();
                    if (newBlock.isEmpty()) {
                        newBlock.setType(block.getType());
                        event.getPlayer().getInventory().getItemInMainHand().subtract(1);
                        event.getPlayer().updateInventory();
                        break;
                    }
                }
            }
        }
    }

    public void setEquipment(EntityEquipment entityEquipment, EntityType entityType, int lvl) {
        switch (lvl) {
            case 1 -> {
                entityEquipment.setHelmet(new ItemStack(Material.IRON_HELMET));
                entityEquipment.setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                entityEquipment.setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                entityEquipment.setBoots(new ItemStack(Material.IRON_BOOTS));

                if (entityType == EntityType.ZOMBIE) {
                    ItemStack sword = new ItemStack(Material.IRON_SWORD);
                    sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    entityEquipment.setItemInMainHand(sword);
                } else if (entityType == EntityType.SKELETON) {
                    ItemStack bow = new ItemStack(Material.BOW);
                    bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                    entityEquipment.setItemInMainHand(bow);
                }
            }
            case 2 -> {
                ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                entityEquipment.setHelmet(helmet);

                ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                entityEquipment.setChestplate(chestplate);

                ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                entityEquipment.setLeggings(leggings);

                ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                entityEquipment.setBoots(boots);

                if (entityType == EntityType.ZOMBIE) {
                    ItemStack sword = new ItemStack(Material.IRON_SWORD);
                    sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
                    entityEquipment.setItemInMainHand(sword);
                } else if (entityType == EntityType.SKELETON) {
                    ItemStack bow = new ItemStack(Material.BOW);
                    bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
                    entityEquipment.setItemInMainHand(bow);
                }
            }
            case 3 -> {
                entityEquipment.setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                entityEquipment.setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                entityEquipment.setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                entityEquipment.setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                if (entityType == EntityType.ZOMBIE) {
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                    sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                    entityEquipment.setItemInMainHand(sword);
                } else if (entityType == EntityType.SKELETON) {
                    ItemStack bow = new ItemStack(Material.BOW);
                    bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
                    entityEquipment.setItemInMainHand(bow);
                }
            }
            default -> {
                ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl - 1);
                entityEquipment.setHelmet(helmet);

                ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl - 1);
                entityEquipment.setChestplate(chestplate);

                ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl - 1);
                entityEquipment.setLeggings(leggings);

                ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvl - 1);
                entityEquipment.setBoots(boots);

                if (entityType == EntityType.ZOMBIE) {
                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                    sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, lvl - 1);
                    entityEquipment.setItemInMainHand(sword);
                } else if (entityType == EntityType.SKELETON) {
                    ItemStack bow = new ItemStack(Material.BOW);
                    bow.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, lvl - 1);
                    entityEquipment.setItemInMainHand(bow);
                }
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            int lvlZombies = EventsController.getInstance().getAccEvents().get(EventsController.EventType.LV_ZOMBIES_LEG);
            if (lvlZombies > 0) {
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () ->
                        setEquipment(zombie.getEquipment(), EntityType.ZOMBIE, lvlZombies), 10L);
            }
        } else if (event.getEntity() instanceof Skeleton skeleton) {
            int lvlSkeleton = EventsController.getInstance().getAccEvents().get(EventsController.EventType.LV_SKELETONS_LEG);
            if (lvlSkeleton > 0) {
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () ->
                        setEquipment(skeleton.getEquipment(), EntityType.SKELETON, lvlSkeleton), 10L);
            }
        } else if (event.getEntity() instanceof Spider spider) {
            int lvlSpider = EventsController.getInstance().getAccEvents().get(EventsController.EventType.LV_SPIDERS_LEG);
            if (lvlSpider > 0) {
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () -> {
                    spider.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 99999, lvlSpider - 1));
                    if (lvlSpider >= 2)
                        spider.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999, 0));
                    if (lvlSpider >= 3)
                        spider.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 99999, lvlSpider - 1));
                    if (lvlSpider >= 4)
                        spider.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999, 0));
                }, 10L);
            }
        }
    }

}
