package com.bukkit.N4th4.NuxNoobs;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.dataholder.worlds.WorldsHolder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NNPermissions {
    private static WorldsHolder wd;

    public static void initialize(NuxNoobs plugin) {
        Plugin p = plugin.getServer().getPluginManager().getPlugin("GroupManager");
        if (p != null) {
            if (!plugin.getServer().getPluginManager().isPluginEnabled(p)) {
                plugin.getServer().getPluginManager().enablePlugin(p);
            }
            GroupManager gm = (GroupManager) p;
            wd = gm.getWorldsHolder();
        } else {
            NNLogger.severe("Plugin GroupManager not found");
            plugin.getPluginLoader().disablePlugin(plugin);
        }
    }

    public static String getGroup(String playerName) {
        return wd.getWorldPermissionsByPlayerName(playerName).getGroup(playerName);
    }

    public static boolean has(Player player, String string) {
        return wd.getWorldPermissions(player).has(player, string);
    }
}