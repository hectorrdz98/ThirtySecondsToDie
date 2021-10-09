package dev.sasukector.thirtysecondstodie.events;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class LegendaryEvents implements Listener {

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
