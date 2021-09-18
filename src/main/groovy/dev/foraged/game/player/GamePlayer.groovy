package dev.foraged.game.player

import dev.foraged.game.data.PlayerData
import dev.foraged.game.util.CC
import org.bukkit.Bukkit

class GamePlayer extends PlayerData {

    int coins

    GamePlayer(UUID id) {
        this.id = id
    }

    void coins(int amount, String reason) {
        coins = coins + amount
        if (Bukkit.getPlayer(id) != null) Bukkit.getPlayer(id).sendMessage(CC.translate("&6+${amount} coins! (${reason})"))
    }
}
