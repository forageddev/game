package dev.foraged.game.item.item

import dev.foraged.game.Game
import dev.foraged.game.item.AbstractGameItem
import dev.foraged.game.task.GameTask
import dev.foraged.game.util.CC
import dev.foraged.game.util.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.metadata.FixedMetadataValue

class ReturnToLobbyItem extends AbstractGameItem {

    Game game

    ReturnToLobbyItem(Game game) {
        super(
                "return-lobby",
                new ItemBuilder(Material.RED_BED)
                        .name("&c&lReturn to Lobby &7(Right Click)")
                        .lore("&7Right-click to leave to the lobby!")
                        .build()
                , 8)
        this.game = game
    }

    @Override
    void onPlayerInteract(PlayerInteractEvent e) {
        super.onPlayerInteract(e)
        Player player = e.player
        if (player.hasMetadata("lobby")) {
            Bukkit.server.scheduler.cancelTask(player.getMetadata("lobby")[0].value() as int)
            player.removeMetadata("lobby", game.plugin)
            player.sendMessage(CC.translate("&c&lTeleport cancelled!"))
        } else {
            player.setMetadata("lobby",  new FixedMetadataValue(game.plugin, new GameTask(game.plugin, () -> (player.hasMetadata("lobby")) ? player.sendMessage(CC.translate("&aSending you to lobby.")) : null).delay(60L).complete().taskId))
            player.sendMessage(CC.translate("&a&lTeleporting you to the lobby in 3 seconds..."))
            player.sendMessage(CC.translate("&a&lRight-click again to cancel the teleport!"))
        }
    }
}
