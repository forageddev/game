package dev.foraged.game.event.impl

import dev.foraged.game.Game
import dev.foraged.game.event.GameEvent

class GameStopEvent extends GameEvent {

    GameStopEvent(Game game) {
        super(game)
    }
}
