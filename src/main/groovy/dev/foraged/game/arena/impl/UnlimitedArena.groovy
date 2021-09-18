package dev.foraged.game.arena.impl

import dev.foraged.game.arena.Arena
import dev.morphia.annotations.Entity
import org.bukkit.Location
import org.bukkit.entity.Player

@Entity("arena")
class UnlimitedArena implements Arena {

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
