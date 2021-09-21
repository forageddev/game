package dev.foraged.game.listener

import dev.foraged.game.ServerGame
import dev.foraged.game.player.TeamPlayer
import dev.foraged.game.util.CC
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import dev.foraged.game.Game
import dev.foraged.game.player.GamePlayer
import dev.foraged.game.player.impl.KillableGamePlayer
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class GamePlayerListener implements Listener {
  
    Game game
  
    GamePlayerListener(Game game) {
		this.game = game
    }

	@EventHandler
	void onJoin(PlayerJoinEvent e) {
		if (!game instanceof ServerGame) return
		Player player = e.player

		e.joinMessage = null
		if (game.gameState.name() == "WAITING" || game.gameState.name() == "STARTING") {
			game.join(player)
		} else {
			e.player.kickPlayer(CC.translate("&cSomething went wrong trying to send you to that server! If this keeps happening please report it! (m001M)")) //TODO: CHANGE THIS
		}
	}

	@EventHandler
	void onQuit(PlayerQuitEvent e) {
		if (!game instanceof ServerGame) return
		Player player = e.player

		e.quitMessage = null
		game.leave(player)
	}

	@EventHandler
	void onDamage(EntityDamageEvent e) {
		if (e.entity instanceof Player && game.gameState.name() == "WAITING" || game.gameState.name() == "STARTING") {
			e.cancelled = true
		}
	}

	@EventHandler
	void onEntityDamageEvent(EntityDamageByEntityEvent e) {
		if (e.damager instanceof Player && e.entity instanceof Player) {
			Player player = e.entity as Player, damager = e.damager as Player
			GamePlayer data = game.getPlayerData(player), damagerData = game.getPlayerData(damager)

			if (data instanceof TeamPlayer && (data as TeamPlayer).team == (damagerData as TeamPlayer).team) {
				player.sendMessage(CC.translate("&c&lYou cannot damage your teammates!"))
				e.cancelled = true
			}
		}
	}
  
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        Player player = e.entity
      
      	GamePlayer data = game.getPlayerData(player)
		
		if (data instanceof KillableGamePlayer) {
			KillableGamePlayer killableData = data as KillableGamePlayer
			killableData.deaths = killableData.deaths + 1
			
			if (player.killer != null) {
				KillableGamePlayer killerData = game.getPlayerData(player.killer) as KillableGamePlayer
				killerData.kills = killerData.kills + 1
			}
		}
    }
}
