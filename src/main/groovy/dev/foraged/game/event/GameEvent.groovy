package dev.foraged.game.event

import dev.foraged.game.Game
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

abstract class GameEvent extends Event {

    private static HandlerList handlers = new HandlerList()

    Game game

    GameEvent(Game game) {
        this.game = game
    }

    @Override
    HandlerList getHandlers() {
        return handlers
    }

    static HandlerList getHandlerList() {
        return handlers
    }
}
