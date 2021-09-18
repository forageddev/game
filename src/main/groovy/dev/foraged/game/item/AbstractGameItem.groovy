package dev.foraged.game.item

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.inventory.ItemStack

abstract class AbstractGameItem implements GameItem {

    String id
    ItemStack item
    int slot

    AbstractGameItem(String id, ItemStack item, int slot) {
        this.id = id
        this.item = item
        this.slot = slot
    }

    @Override
    void onInventoryClick(InventoryClickEvent e) {
        e.setCancelled(true)
    }

    @Override
    void onPlayerInteract(PlayerInteractEvent e) {
        e.setCancelled(true)
    }

    @Override
    void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
        e.setCancelled(true)
    }

    @Override
    void onPlayerDropItem(PlayerDropItemEvent e) {
        e.setCancelled(true)
    }

    @Override
    void onPlayerItemConsume(PlayerItemConsumeEvent e) {
        e.setCancelled(true)
    }

    @Override
    void onPlayerItemHeld(PlayerItemHeldEvent e) {

    }

    @Override
    void onPlayerPickupItem(PlayerPickupItemEvent e) {
        e.setCancelled(true)
    }

    @Override
    void onPlayerItemBreak(PlayerItemBreakEvent e) {

    }
}
