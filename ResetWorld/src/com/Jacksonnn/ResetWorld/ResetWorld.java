package com.Jacksonnn.ResetWorld;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class ResetWorld extends JavaPlugin {

    Plugin plugin = this;

    public void onEnable() {
        this.getCommand("resetworld").setExecutor(new ResetWorldCommand());
        Bukkit.getServer().getPluginManager().registerEvents(new RWListener(), plugin);
        loadConfig();
        Bukkit.getServer().getLogger().info(ChatColor.GREEN + "ResetWorld plugin has been enabled!");

    }

    public void onDisable() {
        Bukkit.getServer().getLogger().info(ChatColor.RED + "ResetWorld plugin has been disabled!");
    }

    public void loadConfig() {
        saveDefaultConfig();
        MultiverseCore mvc = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager mvWM = mvc.getMVWorldManager();

        Collection<MultiverseWorld> worlds = mvWM.getMVWorlds();

        for (MultiverseWorld world : worlds) {
            String name = world.getName();

            getConfig().addDefault("Worlds." + name, true);
        }

    }
}
