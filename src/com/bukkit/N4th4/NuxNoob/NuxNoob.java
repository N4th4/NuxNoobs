package com.bukkit.N4th4.NuxNoob;

import java.io.File;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;

/**
 * NuxNoob for Bukkit
 *
 * @author N4th4
 */
public class NuxNoob extends JavaPlugin {
    private final NuxNoobPlayerListener playerListener;
    private final HashMap<Player, Boolean> debugees;

    public NuxNoob(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
        NuxNoobLogger.initialize();
        playerListener = new NuxNoobPlayerListener(this);
        debugees = new HashMap<Player, Boolean>();
    }
    public void onEnable() {
    	NuxNoobPermissions.initialize(getServer());
    	
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND,  playerListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        NuxNoobLogger.info( pdfFile.getName() + " version " + pdfFile.getVersion() + " est activé !" );
    }
    public void onDisable() {
        NuxNoobLogger.info("Au revoir !");
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

