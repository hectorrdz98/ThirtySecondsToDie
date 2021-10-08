package dev.sasukector.thirtysecondstodie.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NormalEvents implements Listener {

    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (event.getDamager() instanceof Slime slime && slime.getScoreboardTags().contains("custom_slime")) {
                player.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 100, 0));
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Slime slime) {
            if (slime.getName().contains("Espiritu Gelatinoso")) {
                slime.getScoreboardTags().add("custom_slime");
            }
        }
    }

}
