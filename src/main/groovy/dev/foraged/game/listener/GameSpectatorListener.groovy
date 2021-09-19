package dev.foraged.game.listener

import dev.foraged.game.SpectatableGame
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent

class GameSpectatorListener implements Listener {

    SpectatableGame game

    GameSpectatorListener(SpectatableGame game) {
        this.game = game
    }

    void onDamage(EntityDamageEvent e) {
        if (e.entity instanceof Player) {
            Player player = e.entity as Player
            if (game.isSpectating(player)) e.cancelled = true
        }
    }
}
