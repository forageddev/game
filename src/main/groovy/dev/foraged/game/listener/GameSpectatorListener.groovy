package dev.foraged.game.listener

import dev.foraged.game.SpectatableGame
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent

class GameSpectatorListener implements Listener {

    SpectatableGame game

    GameSpectatorListener(SpectatableGame game) {
        this.game = game
    }

    @EventHandler
    void onDamage(EntityDamageByEntityEvent e) {
        if (e.damager instanceof Player) {
            Player player = e.damager as Player
            if (game.isSpectating(player)) e.cancelled = true
        }
    }

    @EventHandler
    void onDamage(EntityDamageEvent e) {
        if (e.entity instanceof Player) {
            Player player = e.entity as Player
            if (game.isSpectating(player)) e.cancelled = true
        }
    }
}
