package dev.foraged.game.arena.impl

import dev.foraged.game.arena.Arena
import dev.foraged.game.util.Cuboid
import dev.morphia.annotations.Entity
import org.bukkit.Location
import org.bukkit.entity.Player

@Entity("arena")
class SimpleArena implements Arena {

    Cuboid cuboid
    Location[] spawnPoints

    SimpleArena(Cuboid cuboid) {
        this.cuboid = cuboid
    }

    @Override
    boolean isInArena(Player player) {
        return cuboid.contains(player)
    }
}
