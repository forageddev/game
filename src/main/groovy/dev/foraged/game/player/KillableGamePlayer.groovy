package dev.foraged.game.player;

class KillableGamePlayer extends GamePlayer {

  int kills, deaths
  
  KillableGamePlayer(UUID id) {
    super(id)
  }
}
