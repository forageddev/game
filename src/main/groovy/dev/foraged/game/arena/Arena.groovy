package dev.foraged.game.arena

import org.bukkit.Location
import org.bukkit.entity.Player

interface Arena {

    Location[] getSpawnPoints()
    boolean isInArena(Player player)

}