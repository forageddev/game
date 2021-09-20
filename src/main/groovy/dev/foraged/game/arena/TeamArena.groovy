package dev.foraged.game.arena

import dev.foraged.game.player.GamePlayerTeam
import org.bukkit.Location

interface TeamArena {

    Map<GamePlayerTeam, Location[]> getTeamSpawnPoints()
    Map<GamePlayerTeam, Integer> getNextTeamSpawnPoint()

    Location getNextTeamSpawnPoint(GamePlayerTeam team)
}