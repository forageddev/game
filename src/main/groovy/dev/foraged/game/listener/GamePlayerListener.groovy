package dev.foraged.game.listener

import org.bukkit.event.Listener
import org.bukkit.event.EventHandler
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.entity.player.Player
import dev.foraged.game.Game
import dev.foraged.game.player.GamePlayer
import dev.foraged.game.player.KillableGamePlayer

class GamePlayerListener implements Listener {
  
    Game game
  
    GamePlayerListener(Game game) {
		this.game = game
    }
  
    @EventHandler
    void onDeath(PlayerDeathEvent e) {
        Player player = e.player
      
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
