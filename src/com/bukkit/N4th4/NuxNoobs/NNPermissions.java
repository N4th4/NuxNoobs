package com.bukkit.N4th4.NuxNoobs;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class NNPermissions {
    private static PermissionHandler Permissions;
    private static Server server;

    public static void initialize(NuxNoobs plugin) {
        server = plugin.getServer();
        Plugin test = server.getPluginManager().getPlugin("Permissions");

        if (test != null) {
            Permissions = ((Permissions) test).getHandler();
        } else {
            NNLogger.info("Permission system not detected, defaulting to OP");
        }
    }

    public static String getGroup(String playerName) {
        return Permissions.getGroup(server.getPlayer(playerName).getWorld().getName(), playerName);
    }

    public static boolean has(Player player, String string) {
        return Permissions.has(player, string);
    }
}