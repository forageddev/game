package dev.foraged.game.task.impl

import dev.foraged.game.Game
import dev.foraged.game.task.GameTask
import org.bukkit.Sound
import org.bukkit.scheduler.BukkitRunnable

class GameCountdownTask extends BukkitRunnable {

    Game game
    int time, fallbackTime, minPlayers

    GameCountdownTask(Game game, int fallbackTime, int minPlayers) {
        this.game = game
        this.time = fallbackTime
        this.fallbackTime = fallbackTime
        this.minPlayers = minPlayers

        new GameTask(game.plugin, this).delay(20L).repeating()
    }

    @Override
    void run() {
        if (game.gameState.name() == "ACTIVE") {
            cancel()
            return
        }

        if (game.players.size() < minPlayers && game.gameState.name() == "STARTING") {
            time = fallbackTime
            game.gameState = "WAITING"
            return
        }

        time--
        if (time == 0) {
            game.start()
            cancel()
        } else if (time % 5 == 0 || time <= 5) {
            var color
            if (time > 20) color = "&b"
            else if (time > 10) color = "&e"
            else if (time > 5) color = "&6"
            else color = "&c"
            game.broadcast("&eThe game starts in ${color}${time}&e second${time == 1 ? "" : "s"}!")
            game.players().each {it.playSound(it.location, Sound.BLOCK_COMPARATOR_CLICK, 1f, 1f)}
        }
    }
}
