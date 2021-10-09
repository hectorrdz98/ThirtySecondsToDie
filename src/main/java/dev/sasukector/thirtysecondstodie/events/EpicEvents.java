package dev.sasukector.thirtysecondstodie.events;

import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import dev.sasukector.thirtysecondstodie.controllers.GameController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class EpicEvents implements Listener {

    @EventHandler
    public void onRegainHealth(EntityRegainHealthEvent event) {
        if (event.getEntity() instanceof Player && event.getRegainReason() == EntityRegainHealthEvent.RegainReason.SATIATED) {
            if (GameController.getInstance().getActiveEvents().stream()
                    .anyMatch(e -> e.getEventType() == EventsController.EventType.UHC_MODE_EPIC)) {
                event.setCancelled(true);
            }
        }
    }

}
