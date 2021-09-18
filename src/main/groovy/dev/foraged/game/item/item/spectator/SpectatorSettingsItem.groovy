package dev.foraged.game.item.item.spectator

import dev.foraged.game.item.AbstractGameItem
import dev.foraged.game.util.CC
import dev.foraged.game.util.ItemBuilder
import org.bukkit.Material
import org.bukkit.event.player.PlayerInteractEvent

class SpectatorSettingsItem extends AbstractGameItem {

    SpectatorSettingsItem() {
        super(
                "spectator-settings",
                new ItemBuilder(Material.COMPARATOR)
                        .name("&b&lSpectator Settings &7(Right Click)")
                        .lore("&7Right-click to change your spectator settings!")
                        .build()
                , 4)
    }

    @Override
    void onPlayerInteract(PlayerInteractEvent e) {
        super.onPlayerInteract(e)

        e.player.sendMessage(CC.translate("&cThis would open the spectator settings."))
    }
}
