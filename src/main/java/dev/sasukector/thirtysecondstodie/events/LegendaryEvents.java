package dev.sasukector.thirtysecondstodie.events;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

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

}
