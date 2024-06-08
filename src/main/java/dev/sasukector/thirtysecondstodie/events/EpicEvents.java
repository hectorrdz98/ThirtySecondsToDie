package dev.sasukector.thirtysecondstodie.events;

import dev.sasukector.thirtysecondstodie.ThirtySecondsToDie;
import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import dev.sasukector.thirtysecondstodie.controllers.GameController;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class EpicEvents implements Listener {

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player
                && event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            if (GameController.getInstance().getActiveEvents().stream()
                    .anyMatch(e -> e.getEventType() == EventsController.EventType.UHC_MODE_EPIC)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (GameController.getInstance().getActiveEvents().stream()
                .anyMatch(e -> e.getEventType() == EventsController.EventType.NO_MOVE_EPIC)) {
            event.setCancelled(true);
        }
        if (GameController.getInstance().getActiveEvents().stream()
                .anyMatch(e -> e.getEventType() == EventsController.EventType.FLOOR_LAVA_EPIC)) {
            Block block = event.getPlayer().getLocation().add(0, -1, 0).getBlock();
            if (block.getType() != Material.LAVA && block.getType() != Material.END_PORTAL_FRAME &&
                    block.getType() != Material.BEDROCK && block.getType() != Material.WATER &&
                    block.getType() != Material.AIR && block.getType() != Material.OBSIDIAN) {
                Bukkit.getScheduler().runTaskLater(ThirtySecondsToDie.getInstance(), () -> block.setType(Material.LAVA),
                        10L);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (GameController.getInstance().getActiveEvents().stream()
                .anyMatch(e -> e.getEventType() == EventsController.EventType.ONLY_BOW_EPIC)) {
            if (event.getDamager() instanceof Player) {
                event.setCancelled(true);
            }
        }
    }

}
