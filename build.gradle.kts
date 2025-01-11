import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask.JarUrl
import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.6.10"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.1"
    id("com.github.ben-manes.versions") version "0.41.0"
    id("com.palantir.git-version") version "0.12.3"
    id("dev.s7a.gradle.minecraft.server") version "1.2.0"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("org.jmailen.kotlinter") version "3.8.0"
}

val gitVersion: Closure<String> by extra

val pluginVersion: String by project.ext

repositories {
    mavenCentral()
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven(url = "https://oss.sonatype.org/content/groups/public/")
}

val shadowImplementation: Configuration by configurations.creating
configurations["implementation"].extendsFrom(shadowImplementation)

dependencies {
    shadowImplementation(kotlin("stdlib"))
    compileOnly("org.spigotmc:spigot-api:$pluginVersion-R0.1-SNAPSHOT")
}

configure<BukkitPluginDescription> {
    main = "com.github.ringoame196_s_mcPlugin.Main"
    version = gitVersion()
    apiVersion = "1." + pluginVersion.split(".")[1]
    depend = listOf("PluginManager")
    website = "https://github.com/ringoame196-s-mcPlugin"
}

tasks.withType<ShadowJar> {
    configurations = listOf(shadowImplementation)
    archiveClassifier.set("")
    relocate("kotlin", "com.github.Ringoame196.libs.kotlin")
    relocate("org.intellij.lang.annotations", "com.github.Ringoame196.libs.org.intellij.lang.annotations")
    relocate("org.jetbrains.annotations", "com.github.Ringoame196.libs.org.jetbrains.annotations")
}

tasks.named("build") {
    dependsOn("shadowJar")
    doFirst {
        copy {
            from(buildDir.resolve("libs/${project.name}.jar"))
            into("D:/デスクトップ/Twitterサーバー/plugins")
        }
    }
}

task<SetupTask>("setup")
