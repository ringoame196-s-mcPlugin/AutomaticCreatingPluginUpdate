package com.github.ringoame196_s_mcPlugin

import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    private val plugin = this
    private val serverManager = ServerManager(plugin)
    override fun onEnable() {
        super.onEnable()
        plugin.saveDefaultConfig() // configファイル生成
        serverManager.start() // サーバー起動
    }

    override fun onDisable() {
        super.onDisable()
        serverManager.stop() // サーバーストップ
    }
}