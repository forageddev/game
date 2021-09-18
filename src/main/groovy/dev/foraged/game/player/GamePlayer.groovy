package dev.foraged.game.player

import dev.foraged.game.data.PlayerData

class GamePlayer extends PlayerData {

    double coins

    GamePlayer(UUID id) {
        this.id = id
    }
}
