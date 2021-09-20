package dev.foraged.game.arena

import org.bukkit.Location
import org.bukkit.entity.Player

interface Arena {

    String getName()
    Location[] getSpawnPoints()
    boolean isInArena(Player player)
    int getNextSpawnPoint()
    void setNextSpawnPoint(int spawnPoint)

    Location getSpawnPoint(Player player)
}