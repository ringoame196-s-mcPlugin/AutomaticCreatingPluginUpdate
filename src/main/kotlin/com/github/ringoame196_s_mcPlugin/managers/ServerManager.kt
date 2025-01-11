package com.github.ringoame196_s_mcPlugin.managers

import com.sun.net.httpserver.HttpServer
import net.md_5.bungee.api.chat.ClickEvent
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.OutputStream
import java.net.InetSocketAddress

class ServerManager(private val plugin: JavaPlugin) {
    private val port = plugin.config.getInt("Port")
    private val server: HttpServer = HttpServer.create(InetSocketAddress(port), 0)

    fun start() {
        server.createContext("/plugin") { exchange ->
            val config = plugin.config // configファイル
            val query = exchange.requestURI.query
            val pluginName = query?.split("=")?.getOrNull(1) ?: "null" // プラグイン名取得

            val response: String // webサイトに表示させるメッセージ
            val reloadPlugin = acquisitionPlugin(pluginName)

            if (reloadPlugin == null) {
                response = "pluginNotFound"
            } else {
                response = "Reload $pluginName"
                val command = config.getString("ReloadCommand")?.replace("@pluginName", pluginName)
                    ?: "/pluginmanager reload $pluginName"
                sendClickableCommandMessage(command, pluginName)
            }

            // webサイトの内容を書き換える
            exchange.sendResponseHeaders(200, response.length.toLong())
            exchange.responseBody.use { os: OutputStream ->
                os.write(response.toByteArray())
            }
        }

        server.executor = null // default executor
        server.start()
        Bukkit.getLogger().info("[${plugin.name}] Server started on port $port") // 通知
    }

    fun stop() {
        server.stop(1) // サーバーを止める
        Bukkit.getLogger().info("[${plugin.name}] Server stopped")
    }

    private fun acquisitionPlugin(pluginName: String): Plugin? { // プラグインがあるか確認
        return Bukkit.getPluginManager().getPlugin(pluginName)
    }

    private fun sendClickableCommandMessage(command: String, pluginName: String) { // プラグインリロードメッセージ 送信
        // メインメッセージ部分
        val mainMessage = TextComponent("${ChatColor.YELLOW}[${plugin.name}] プラグイン名($pluginName) ")

        // クリック可能なリロード部分
        val reloadComponent = TextComponent("${ChatColor.AQUA}[リロード]")

        // ホバーテキストを設定
        reloadComponent.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, ComponentBuilder("クリックしてプラグインをリロードします").create())

        // クリック時にコマンド実行
        reloadComponent.clickEvent = ClickEvent(ClickEvent.Action.RUN_COMMAND, command)

        // メインメッセージにリロード部分を追加
        mainMessage.addExtra(reloadComponent)

        // メッセージをOPプレイヤーに送信
        for (player in Bukkit.getOnlinePlayers()) {
            if (!player.isOp) {
                continue
            }
            player.spigot().sendMessage(mainMessage)
        }
    }
}
