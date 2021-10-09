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
import org.bukkit.inventory.ItemStack;

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

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Zombie zombie) {
            int lvlZombies = EventsController.getInstance().getAccEvents().get(EventsController.EventType.LV_ZOMBIES_LEG);
            if (lvlZombies > 0) {
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () -> {
                    switch (lvlZombies) {
                        case 1 -> {
                            zombie.getEquipment().setHelmet(new ItemStack(Material.IRON_HELMET));
                            zombie.getEquipment().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                            zombie.getEquipment().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                            zombie.getEquipment().setBoots(new ItemStack(Material.IRON_BOOTS));

                            ItemStack sword = new ItemStack(Material.IRON_SWORD);
                            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                            zombie.getEquipment().setItemInMainHand(sword);
                        }
                        case 2 -> {
                            ItemStack helmet = new ItemStack(Material.IRON_HELMET);
                            helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setHelmet(helmet);

                            ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setChestplate(chestplate);

                            ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setLeggings(leggings);

                            ItemStack boots = new ItemStack(Material.IRON_BOOTS);
                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setBoots(boots);

                            ItemStack sword = new ItemStack(Material.IRON_SWORD);
                            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
                            zombie.getEquipment().setItemInMainHand(sword);
                        }
                        case 3 -> {
                            zombie.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                            zombie.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                            zombie.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                            zombie.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
                            zombie.getEquipment().setItemInMainHand(sword);
                        }
                        case 4 -> {
                            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                            helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setHelmet(helmet);

                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setChestplate(chestplate);

                            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setLeggings(leggings);

                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 3);
                            zombie.getEquipment().setBoots(boots);

                            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
                            zombie.getEquipment().setItemInMainHand(sword);
                        }
                        default -> {
                            ItemStack helmet = new ItemStack(Material.DIAMOND_HELMET);
                            helmet.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvlZombies - 1);
                            zombie.getEquipment().setHelmet(helmet);

                            ItemStack chestplate = new ItemStack(Material.DIAMOND_CHESTPLATE);
                            chestplate.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvlZombies - 1);
                            zombie.getEquipment().setChestplate(chestplate);

                            ItemStack leggings = new ItemStack(Material.DIAMOND_LEGGINGS);
                            leggings.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvlZombies - 1);
                            zombie.getEquipment().setLeggings(leggings);

                            ItemStack boots = new ItemStack(Material.DIAMOND_BOOTS);
                            boots.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, lvlZombies - 1);
                            zombie.getEquipment().setBoots(boots);

                            ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                            sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, lvlZombies - 1);
                            zombie.getEquipment().setItemInMainHand(sword);
                        }
                    }
                }, 10L);
            }
        }
    }

}
