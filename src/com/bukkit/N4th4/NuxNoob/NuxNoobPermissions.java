package com.bukkit.N4th4.NuxNoob;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijikokun.bukkit.Permissions.Permissions;

public class NuxNoobPermissions {
	private static Permissions permissions;
	
	public static void initialize(Server server) {
        Plugin test = server.getPluginManager().getPlugin("Permissions");

        if(test != null) {
        	permissions = (Permissions)test;
        	NuxNoobLogger.info("Plug-in Permission trouvé.");
        } else {
        	NuxNoobLogger.severe("Plug-in Permission non trouvé.");
        }
    }
	public static String getGroup(String playerName) {
		return permissions.Security.getGroup(playerName);
	}
	public static boolean has(Player player, String string) {
		return permissions.Security.has(player, string);
	}
}