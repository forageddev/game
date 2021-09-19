package dev.foraged.game.arena.impl

import com.grinderwolf.swm.api.SlimePlugin
import com.grinderwolf.swm.api.loaders.SlimeLoader
import com.grinderwolf.swm.api.world.SlimeWorld
import com.grinderwolf.swm.api.world.properties.SlimeProperties
import com.grinderwolf.swm.api.world.properties.SlimePropertyMap
import dev.foraged.game.Game
import dev.foraged.game.arena.Arena
import dev.morphia.annotations.Entity
import dev.morphia.annotations.Id
import dev.morphia.annotations.Transient
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player

@Entity("arena")
class SlimeArena implements Arena {

    @Id String name
    String levelName
    Location[] spawnPoints

    @Transient
    SlimeWorld world

    SlimeArena(String name, String levelName) {
        this.name = name
        this.levelName = levelName

        if (Bukkit.pluginManager.getPlugin("SlimeWorldManager") == null) throw new RuntimeException("Slime Arena Requires [SlimeWorldManager] to be installed")

        SlimePlugin slimePlugin = Bukkit.pluginManager.getPlugin("SlimeWorldManager") as SlimePlugin
        SlimeLoader loader = slimePlugin.getLoader("file")

        world = slimePlugin.loadWorld(loader, levelName, true, new SlimePropertyMap()).clone(levelName + "-" + UUID.randomUUID().toString().split("-")[0])
        slimePlugin.generateWorld(world)
    }

    @Override
    boolean isInArena(Player player) {
        return player.world.name == world.name
    }

    @Override
    Location getSpawnPoint(Player player) {
        return null
    }
}
