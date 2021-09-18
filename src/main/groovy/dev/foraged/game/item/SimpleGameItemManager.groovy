package dev.foraged.game.item

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SimpleGameItemManager extends GameItemManager {

    SimpleGameItemManager(JavaPlugin plugin) {
        Bukkit.server.pluginManager.registerEvents(this, plugin)
    }
}
