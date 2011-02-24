package com.bukkit.N4th4.NuxNoobs;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

public class NuxNoobs extends JavaPlugin {
    private final NNPlayerListener playerListener;
    private final HashMap<Player, Boolean> debugees;

    public NuxNoobs() {
        NNLogger.initialize();
        playerListener = new NNPlayerListener(this);
        debugees = new HashMap<Player, Boolean>();
    }

    public void onEnable() {
        NNPermissions.initialize(getServer());

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND, playerListener, Priority.Normal, this);
    }

    public void onDisable() {
    }

    public boolean isDebugging(final Player player) {
        if (debugees.containsKey(player)) {
            return debugees.get(player);
        } else {
            return false;
        }
    }

    public void setDebugging(final Player player, final boolean value) {
        debugees.put(player, value);
    }
}
