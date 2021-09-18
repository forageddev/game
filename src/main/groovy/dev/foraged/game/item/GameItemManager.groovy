package dev.foraged.game.item

import dev.foraged.game.util.ItemBuilder
import org.bukkit.event.EventHandler
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

abstract class GameItemManager implements Listener {

    Map<ItemStack, GameItem> gameItems = new HashMap<>()
    Map<String, GameItemBundle> gameItemBundles = new HashMap<>()

    GameItem registerItem(GameItem item) {
        Optional<GameItem> it = gameItems.values().stream().filter(it -> it.id == item.id).findFirst()
        if (it.present) return it.get()
        gameItems.put(item.item, item)
        return item
    }

    GameItemBundle registerBundle(GameItemBundle bundle) {
        gameItemBundles.put(bundle.id, bundle)
        return bundle
    }

    GameItemBundle getItemBundle(String id) {
        return gameItemBundles.containsKey(id) ? gameItemBundles.get(id) : null
    }

    GameItem getGameItem(ItemStack stack) {
        if (stack == null) return null
        stack = new ItemBuilder(stack.clone()).amount(1).build()
        return gameItems.containsKey(stack) ? gameItems.get(stack) : null
    }

    boolean isGameItem(ItemStack stack) {
        return getGameItem(stack) != null
    }

    @EventHandler
    void onEvent(InventoryClickEvent e) {
        ItemStack stack = e.currentItem
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onInventoryClick(e)
    }

    @EventHandler
    void onEvent(PlayerInteractEvent e) {
        ItemStack stack = e.item
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerInteract(e)
    }

    @EventHandler
    void onEvent(PlayerDropItemEvent e) {
        ItemStack stack = e.itemDrop.itemStack
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerDropItem(e)
    }

    @EventHandler
    void onEvent(PlayerItemConsumeEvent e) {
        ItemStack stack = e.item
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerItemConsume(e)
    }

    @EventHandler
    void onEvent(PlayerItemHeldEvent e) {
        ItemStack stack = e.player.inventory.getItem(e.getNewSlot())
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerItemHeld(e)
    }

    @EventHandler
    void onEvent(PlayerPickupItemEvent e) {
        ItemStack stack = e.item.itemStack
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerPickupItem(e)
    }

    @EventHandler
    void onEvent(PlayerItemBreakEvent e) {
        ItemStack stack = e.brokenItem
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerItemBreak(e)
    }

    @EventHandler
    void onEvent(PlayerInteractEntityEvent e) {
        ItemStack stack = e.player.itemInHand
        GameItem gameItem = getGameItem(stack)
        if (gameItem == null) return

        gameItem.onPlayerInteractEntity(e)
    }
}
