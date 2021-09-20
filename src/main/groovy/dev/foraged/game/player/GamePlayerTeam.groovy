package dev.foraged.game.player

import org.bukkit.ChatColor
import org.bukkit.DyeColor
import org.bukkit.Material

enum GamePlayerTeam {


    RED(ChatColor.RED, DyeColor.RED),
    BLUE(ChatColor.BLUE, DyeColor.BLUE),
    GREEN(ChatColor.GREEN, DyeColor.LIME),
    YELLOW(ChatColor.YELLOW, DyeColor.YELLOW),
    CYAN(ChatColor.DARK_AQUA, DyeColor.CYAN),
    WHITE(ChatColor.WHITE, DyeColor.WHITE),
    PINK(ChatColor.LIGHT_PURPLE, DyeColor.PINK),
    GRAY(ChatColor.DARK_GRAY, DyeColor.GRAY)

    ChatColor color
    DyeColor dye

    GamePlayerTeam(ChatColor color, DyeColor dye) {
        this.color = color
        this.dye = dye
    }

    String getDisplayName() {
        return color.toString() + name().toLowerCase().capitalize()
    }

    Material getWool() {
        return Material.valueOf("${dye.name()}_WOOL")
    }

    Material getStainedClay() {
        return Material.valueOf("${dye.name()}_STAINED_CLAY")
    }

    Material getStainedGlass() {
        return Material.valueOf("${dye.name()}_STAINED_GLASS")
    }

    GamePlayerTeam getNext() {
        if (this == BLUE) return RED
        return values()[ordinal() + 1]
    }

    static GamePlayerTeam nextTeam = RED

    static GamePlayerTeam nextTeam() {
        var team = nextTeam
        nextTeam = nextTeam.next
        return team
    }
}
