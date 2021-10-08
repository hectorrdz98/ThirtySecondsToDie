package dev.sasukector.thirtysecondstodie.events;

import dev.sasukector.thirtysecondstodie.controllers.EventsController;
import dev.sasukector.thirtysecondstodie.controllers.GameController;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.InventoryHolder;

public class RareEvents implements Listener {

    @EventHandler
    public void blockChestInteract(PlayerInteractEvent event) {
        if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(event.getClickedBlock() != null && event.getClickedBlock().getState() instanceof InventoryHolder) {
                if (GameController.getInstance().getActiveEvents().stream()
                        .anyMatch(e -> e.getEventType() == EventsController.EventType.NO_CHESTS_RARE)) {
                    event.setCancelled(true);
                }
            }
        }
    }

}
