package com.github.ringoame196_s_mcPlugin

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class ReloadManager(private val plugin: Plugin) {
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
}
