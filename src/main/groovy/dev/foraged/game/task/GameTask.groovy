package dev.foraged.game.task

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask

class GameTask {

    JavaPlugin plugin

    Runnable runnable
    long delay = 0L
    boolean async

    GameTask(JavaPlugin plugin, Runnable runnable) {
        this.plugin = plugin
        this.runnable = runnable
        if (this.runnable == null) {
            this.runnable = () -> {}
        }
    }

    GameTask delay(long delay) {
        this.delay = delay
        return this
    }

    GameTask async(boolean value) {
        this.async = value
        return this
    }

    GameTask async() {
        async(!async)
    }

    GameTask then(Runnable r) {
        if (delay != 0) {
            if (async) {
                Bukkit.server.scheduler.runTaskLaterAsynchronously(plugin, runnable, delay)
            } else {
                Bukkit.server.scheduler.runTaskLater(plugin, runnable, delay)
            }
        } else {
            if (async) {
                Bukkit.server.scheduler.runTaskAsynchronously(plugin, runnable)
            } else {
                Bukkit.server.scheduler.runTask(plugin, runnable)
            }
        }

        return new GameTask(plugin, r)
    }

    BukkitTask complete() {
        if (delay != 0) {
            if (async) {
                return Bukkit.server.scheduler.runTaskLaterAsynchronously(plugin, runnable, delay)
            } else {
                return Bukkit.server.scheduler.runTaskLater(plugin, runnable, delay)
            }
        } else {
            if (async) {
                return Bukkit.server.scheduler.runTaskAsynchronously(plugin, runnable)
            } else {
                return Bukkit.server.scheduler.runTask(plugin, runnable)
            }
        }
    }

    void repeating() {
        if (delay == 0) throw new RuntimeException("Put a delay idiot boi")

        if (async) {
            Bukkit.server.scheduler.scheduleAsyncRepeatingTask(plugin, runnable, delay, delay)
        } else {
            Bukkit.server.scheduler.scheduleSyncRepeatingTask(plugin, runnable, delay, delay)
        }
    }
}
