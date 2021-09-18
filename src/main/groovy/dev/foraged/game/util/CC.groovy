package dev.foraged.game.util

import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor

import java.util.stream.Collectors

class CC {

    static String translate(String string) {
        return ChatColor.translateAlternateColorCodes('&' as char, string)
    }

    static List<String> translate(List<String> lore) {
        return lore.stream().map(CC::translate).collect(Collectors.toList())
    }

    static String center(String string) {
        return translate(StringUtils.center(string, 64))
    }
}
