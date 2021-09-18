package dev.foraged.game.board

import io.github.thatkawaiisam.assemble.AssembleAdapter
import org.bukkit.entity.Player

interface GameBoardAdapter extends AssembleAdapter {

    String getTitle(Player player);
    List<String> getLines(Player player);
}