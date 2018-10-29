package com.Jacksonnn.ResetWorld;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

public class ResetWorldCommand implements CommandExecutor {
    private Plugin plugin = ResetWorld.getPlugin(ResetWorld.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        ArrayList<String> resetWorlds = new ArrayList<>();

        if (args.length == 0) {
            sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset allworlds - Resets all of the worlds on the server.");
            sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset world <world> - Resets the specified world.");
            sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset enable <world> - Enables the world in the config (Allows allworlds command).");
            sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset disable <world> - Disables the world in the config (Disallows allworlds command).");
        } else {
            if (sender.hasPermission("worldreset.use")) {
                MultiverseCore mvc = (MultiverseCore) Bukkit.getPluginManager().getPlugin("Multiverse-Core");
                MVWorldManager mvWM = mvc.getMVWorldManager();

                if (args[0].equalsIgnoreCase("world")) {
                    String worldName = args[1];
                    MultiverseWorld world = mvWM.getMVWorld(worldName);
                    String seed = Long.toString(world.getSeed());
                    World.Environment environment = world.getEnvironment();

                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "Resetting the world, " + worldName + ".");

                    mvWM.deleteWorld(worldName, true, true);
                    mvWM.unloadWorld(worldName, true);
                    mvWM.addWorld(worldName, environment, seed, null, true, null);

                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "The world, " + worldName + ", has successfully been reset!");

                } else if (args[0].equalsIgnoreCase("allworlds")) {
                    Collection<MultiverseWorld> worlds = mvWM.getMVWorlds();

                    //Check Config
                    for (MultiverseWorld world : worlds) {
                        String name = world.getName();

                        if (!plugin.getConfig().getBoolean("Worlds." + name)) {
                            worlds.remove(world);
                        }

                    }

                    //Purge world
                    for (MultiverseWorld world : worlds) {
                        String worldName = world.getName();
                        String seed = Long.toString(world.getSeed());
                        World.Environment environment = world.getEnvironment();

                        sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "Resetting the world, " + worldName + ".");

                        mvWM.deleteWorld(worldName, true, true);
                        mvWM.unloadWorld(worldName, true);
                        mvWM.addWorld(worldName, environment, seed, null, true, null);

                        resetWorlds.add(worldName);
                    }

                    String formatted = String.join(", " + resetWorlds);

                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "The following worlds have successfully been reset:");
                    sender.sendMessage(formatted);
                } else if (args[0].equalsIgnoreCase("enable")) {
                    plugin.getConfig().set("World." + args[1], true);
                    plugin.saveConfig();
                } else if (args[0].equalsIgnoreCase("disable")) {
                    plugin.getConfig().set("World." + args[1], false);
                    plugin.saveConfig();
                } else {
                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset allworlds - Resets all of the worlds on the server.");
                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset world <world> - Resets the specified world.");
                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset enable <world> - Enables the world in the config (Allows allworlds command).");
                    sender.sendMessage(ChatColor.YELLOW + "[WorldReset] " + ChatColor.WHITE + "/worldreset disable <world> - Disables the world in the config (Disallows allworlds command).");
                    return true;
                }
            }
        }
        return true;
    }
}
