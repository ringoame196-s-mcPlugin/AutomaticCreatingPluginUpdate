package com.github.ringoame196_s_mcPlugin.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.plugin.Plugin

class Command(plugin: Plugin) : CommandExecutor, TabCompleter {
    private val config = plugin.config

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) return false

        val pluginName = args[0]
        reloadPlugin(sender, pluginName)

        return true
    }

    private fun reloadPlugin(sender: CommandSender, pluginName: String) {
        val command = config.getString("ReloadCommand")?.replace("@pluginName", pluginName)
            ?: "pluginmanager reload $pluginName"
        Bukkit.dispatchCommand(sender, command)
    }

    override fun onTabComplete(commandSender: CommandSender, command: Command, label: String, args: Array<out String>): MutableList<String>? {
        return when (args.size) {
            1 -> mutableListOf("[プラグイン名]")
            else -> mutableListOf()
        }
    }
}
