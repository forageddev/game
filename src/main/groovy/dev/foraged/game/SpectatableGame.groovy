package dev.foraged.game

import org.bukkit.entity.Player

interface SpectatableGame {

    void startSpectating(Player player)
    boolean isSpectating(Player player)

    Collection<? extends Player> alivePlayers();
}