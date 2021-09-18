package dev.foraged.game.item

import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEntityEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.bukkit.event.player.PlayerItemConsumeEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerPickupItemEvent
import org.bukkit.inventory.ItemStack

interface GameItem extends Listener {

    String getId()
    ItemStack getItem()
    int getSlot()

    void onInventoryClick(InventoryClickEvent e)
    void onPlayerInteract(PlayerInteractEvent e)
    void onPlayerInteractEntity(PlayerInteractEntityEvent e)
    void onPlayerDropItem(PlayerDropItemEvent e)
    void onPlayerItemConsume(PlayerItemConsumeEvent e)
    void onPlayerItemHeld(PlayerItemHeldEvent e)
    void onPlayerPickupItem(PlayerPickupItemEvent e)
    void onPlayerItemBreak(PlayerItemBreakEvent e)
}