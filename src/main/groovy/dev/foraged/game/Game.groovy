package dev.foraged.game

import dev.foraged.game.arena.Arena
import dev.foraged.game.board.GameBoardAdapter
import dev.foraged.game.event.impl.GameStartEvent
import dev.foraged.game.event.impl.GameStopEvent
import dev.foraged.game.item.GameItemBundle
import dev.foraged.game.item.GameItemManager
import dev.foraged.game.item.SimpleGameItemManager
import dev.foraged.game.item.item.spectator.SpectatorPlayAgainItem
import dev.foraged.game.item.item.ReturnToLobbyItem
import dev.foraged.game.item.item.spectator.SpectatorSettingsItem
import dev.foraged.game.item.item.spectator.SpectatorTeleporterItem
import dev.foraged.game.player.GamePlayer
import dev.foraged.game.util.CC
import io.github.thatkawaiisam.assemble.Assemble
import io.github.thatkawaiisam.assemble.AssembleStyle
import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

import java.util.stream.Collectors

abstract class Game<P extends GamePlayer, A extends Arena> {

    static Game instance
    JavaPlugin plugin

    String name, description
    GameItemManager gameItemManager

    A arena
    Map<UUID, P> players = new HashMap<>()
    long started

    Game(JavaPlugin plugin, String name, String description) {
        instance = this
        this.plugin = plugin
        this.name = name
        this.description = description
        this.gameItemManager = new SimpleGameItemManager(plugin)

        if (this instanceof GameBoardAdapter) {
            GameBoardAdapter adapter = this as GameBoardAdapter

            Assemble assemble = new Assemble(plugin, adapter)
            assemble.ticks = 2L
            assemble.assembleStyle = AssembleStyle.KOHI
        }

        this.gameItemManager.registerBundle(
                new GameItemBundle(
                        "lobby",
                        this.gameItemManager.registerItem(new ReturnToLobbyItem(this))
                )
        )
        if (this instanceof SpectatableGame) {
            this.gameItemManager.registerBundle(
                    new GameItemBundle(
                            "spectator",
                            this.gameItemManager.registerItem(new SpectatorTeleporterItem()),
                            this.gameItemManager.registerItem(new SpectatorSettingsItem()),
                            this.gameItemManager.registerItem(new SpectatorPlayAgainItem()),
                            this.gameItemManager.registerItem(new ReturnToLobbyItem(this)),
                    )
            )
        }
    }

    var _nextSpawnPoint = 0

    P getPlayerData(UUID id) {
        return players.get(id)
    }

    P getPlayerData(Player player) {
        return players.get(player.uniqueId)
    }

    abstract void ready()

    void start() {
        started = System.currentTimeMillis()
        new GameStartEvent(this).callEvent()
    }

    void stop() {
        new GameStopEvent(this).callEvent()
        List<P> rankings = players.values().stream().sorted(Comparator.naturalOrder()).limit(3).collect(Collectors.toList())
        buildWrapper(rankings.stream().map(s -> "&e&l${rankings.indexOf(s) + 1} Place: &7${Bukkit.getOfflinePlayer(s.id).name}").collect(Collectors.joining("\n", "", ""))).each {broadcast(it)}
    }

    void join(Player player) {
        gameItemManager.getItemBundle("lobby").apply(player)
        player.teleport(arena.spawnPoints[_nextSpawnPoint])
        if (_nextSpawnPoint == arena.spawnPoints.size()) _nextSpawnPoint = 0

        broadcast("&7${player.name} &ehas joined (&b${players.size() + 1}&e/&b${Bukkit.maxPlayers}&e)!")

        buildWrapper(description.split("\n").toList().stream().map(it -> CC.center("&e&l${it}")).collect(Collectors.joining("\n", "", ""))).each {player.sendMessage(CC.translate(it))}

        /*player.sendMessage(CC.translate(StringUtils.repeat("&a▬", 80)))
        player.sendMessage(CC.center("&f&l${name}"))
        player.sendMessage("")
        description.split("\n").each {
            player.sendMessage(CC.center("&e&l${it}"))
        }
        player.sendMessage(CC.translate(StringUtils.repeat("&a▬", 80)))*/
    }

    void leave(Player player) {
        players.remove(player.uniqueId)
        broadcast("&7${player.name} &ehas quit!")
    }

    void broadcast(String message) {
        players().forEach(player -> {
            player.sendMessage(CC.translate(message))
        })
    }

    String[] buildWrapper(String title, String message) {
        Arrays.asList(
                CC.translate(StringUtils.repeat("&a▬", 80)),
                CC.center("&f&l${title}"),
                "",
                message,
                CC.translate(StringUtils.repeat("&a▬", 80)))
    }

    String[] buildWrapper(String message) {
        buildWrapper(name, message)
    }

    Collection<? extends Player> players() {
        return Bukkit.getServer().getOnlinePlayers().stream().filter(player -> getPlayerData(player) != null).collect(Collectors.toList())
    }
}