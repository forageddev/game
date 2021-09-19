package dev.foraged.game.arena.impl

import dev.foraged.game.arena.Arena
import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import org.bukkit.Location
import org.bukkit.entity.Player

class UnlimitedArena implements Arena {

    String name = "Unlimited"
    Location[] spawnPoints

    @Override
    boolean isInArena(Player player) {
        return true
    }

    @Override
    Location getSpawnPoint(Player player) {
        return null
    }
}
