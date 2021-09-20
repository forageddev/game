package dev.foraged.game.arena.impl.team

import dev.foraged.game.Game
import dev.foraged.game.arena.TeamArena
import dev.foraged.game.arena.impl.SlimeArena
import dev.foraged.game.player.GamePlayerTeam
import dev.foraged.game.player.TeamPlayer
import org.bukkit.Location
import org.bukkit.entity.Player

abstract class TeamSlimeArena extends SlimeArena implements TeamArena {

    Game game

    Map<GamePlayerTeam, Location[]> teamSpawnPoints = new HashMap<>()
    Map<GamePlayerTeam, Integer> nextTeamSpawnPoint = new HashMap<>()

    TeamSlimeArena(String name, String levelName, Game game) {
        super(name, levelName)
        this.game = game
    }

    void addSpawnPoints(GamePlayerTeam team, Location... spawns) {
        teamSpawnPoints.put(team, spawns)
        if (!nextTeamSpawnPoint.containsKey(team)) nextTeamSpawnPoint[team] = 0
    }

    @Override
    Location getNextTeamSpawnPoint(GamePlayerTeam team) {
        int next = nextTeamSpawnPoint[team] + 1
        if (teamSpawnPoints[team].size() >= next) next = 0
        nextTeamSpawnPoint.put(team, next)
        return teamSpawnPoints[team][next]
    }

    @Override
    Location getSpawnPoint(Player player) {
        return getNextTeamSpawnPoint((game.getPlayerData(player) as TeamPlayer).team)
    }
}
