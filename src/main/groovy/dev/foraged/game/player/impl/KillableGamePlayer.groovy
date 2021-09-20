package dev.foraged.game.player.impl

import dev.foraged.game.player.GamePlayer;

class KillableGamePlayer extends GamePlayer {

  int kills, deaths
  
  KillableGamePlayer(UUID id) {
      super(id)
  }
}
