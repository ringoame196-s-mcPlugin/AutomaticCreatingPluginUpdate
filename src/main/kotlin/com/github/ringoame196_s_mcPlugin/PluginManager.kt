package com.github.ringoame196_s_mcPlugin

import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class PluginManager(private val plugin: Plugin) {
    private val config = plugin.config

    fun reloadPlugin(sender: CommandSender, pluginName: String) {
        if (Data.reloadablePlugin.contains(pluginName)) {
            Data.reloadablePlugin.remove(pluginName)
            val command = config.getString("ReloadCommand")?.replace("@pluginName", pluginName)
                ?: "pluginmanager reload $pluginName"
            Bukkit.dispatchCommand(sender, command)
        } else {
            val message = "${ChatColor.RED}既にリロードされています"
            sender.sendMessage(message)
        }
    }

    fun autoReload(pluginName: String): Boolean {
        val randomPlayer = Bukkit.getOnlinePlayers().random() ?: return false
        val sender: CommandSender = randomPlayer
        Bukkit.getScheduler().runTask(
            plugin,
            Runnable {
                reloadPlugin(sender, pluginName)
            }
        )
        return true
    }

    fun acquisitionPlugin(pluginName: String): Plugin? { // プラグインがあるか確認
        return Bukkit.getPluginManager().getPlugin(pluginName)
    }

    fun sendOpMessage(message: TextComponent) {
        // メッセージをOPプレイヤーに送信
        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.isOp) {
                continue
            }
            player.spigot().sendMessage(message)
        }
    }

    fun sendOpMessage(message: String) {
        // メッセージをOPプレイヤーに送信
        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.isOp) {
                continue
            }
            player.sendMessage(message)
        }
    }
}
