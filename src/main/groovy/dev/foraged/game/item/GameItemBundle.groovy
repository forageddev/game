package dev.foraged.game.item

import org.bukkit.entity.Player

class GameItemBundle {

    String id
    GameItem[] items

    GameItemBundle(String id, GameItem... items) {
        this.id = id
        this.items = items
    }

    void apply(Player player) {
        player.inventory.clear()
        items.each {
            player.inventory.setItem(it.slot, it.item)
        }
    }
}
