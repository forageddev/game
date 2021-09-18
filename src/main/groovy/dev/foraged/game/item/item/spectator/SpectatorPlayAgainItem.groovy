package dev.foraged.game.item.item.spectator

import dev.foraged.game.item.AbstractGameItem
import dev.foraged.game.util.CC
import dev.foraged.game.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class SpectatorPlayAgainItem extends AbstractGameItem {

    SpectatorPlayAgainItem() {
        super(
                "spectator-play-again",
                new ItemBuilder(Material.PAPER)
                        .name("&b&lPlay Again &7(Right Click)")
                        .lore("&7Right-click to play another game!")
                        .build()
                , 7)
    }

    @Override
    void onPlayerInteract(PlayerInteractEvent e) {
        super.onPlayerInteract(e)

        e.player.sendMessage(CC.translate("&cThis would try to find you a new game."))
    }
}
