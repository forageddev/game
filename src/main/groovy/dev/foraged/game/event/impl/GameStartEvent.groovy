package dev.foraged.game.event.impl

import dev.foraged.game.Game
import dev.foraged.game.event.GameEvent

class GameStartEvent extends GameEvent {

    GameStartEvent(Game game) {
        super(game)
    }
}
