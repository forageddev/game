package dev.foraged.game.player

import dev.foraged.game.Game
import dev.foraged.game.SpectatableGame
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
        if (this.ordinal() == (System.getProperty("team-count") as int) - 1) return RED
        return values()[ordinal() + 1]
    }

    TeamPlayer[] alive(SpectatableGame game) {
        return game.alivePlayers().stream().map(p -> (game as Game).getPlayerData(p)).filter(t -> (t as TeamPlayer).team == this).toArray() as TeamPlayer[]
    }

    static GamePlayerTeam nextTeam = RED

    static GamePlayerTeam nextTeam() {
        var team = nextTeam
        nextTeam = nextTeam.next
        return team
    }

    static GamePlayerTeam[] enabled() {
        return values().toList().stream().filter(t -> t.ordinal() < (System.getProperty("team-count") as int)).toArray() as GamePlayerTeam[]
    }
}
