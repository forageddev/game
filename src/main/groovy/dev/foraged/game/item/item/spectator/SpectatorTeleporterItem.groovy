package dev.foraged.game.item.item.spectator

import dev.foraged.game.item.AbstractGameItem
import dev.foraged.game.util.CC
import dev.foraged.game.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class SpectatorTeleporterItem extends AbstractGameItem {

    SpectatorTeleporterItem() {
        super(
                "spectator-teleporter",
                new ItemBuilder(Material.COMPASS)
                        .name("&a&lTeleporter &7(Right Click)")
                        .lore("&7Right-click to spectate players!")
                        .build()
                , 0)
    }

    @Override
    void onPlayerInteract(PlayerInteractEvent e) {
        super.onPlayerInteract(e)

        e.player.sendMessage(CC.translate("&cThis would open the spectator menu."))
    }
}
