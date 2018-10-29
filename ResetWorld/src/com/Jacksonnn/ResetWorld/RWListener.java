package com.Jacksonnn.ResetWorld;

import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.event.MVWorldDeleteEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.plugin.Plugin;

public class RWListener implements Listener {
    Plugin plugin = ResetWorld.getPlugin(ResetWorld.class);

    public void onWorldDeletion(MVWorldDeleteEvent event) {
        MultiverseWorld world = event.getWorld();
        plugin.getConfig().set("Worlds." + world.getName(), "");
        plugin.saveConfig();
    }

    public void onWorldCreation(WorldInitEvent event) {
        String name = event.getWorld().getName();
        plugin.getConfig().addDefault("Worlds." + name, "true");
        plugin.saveConfig();
    }
}
