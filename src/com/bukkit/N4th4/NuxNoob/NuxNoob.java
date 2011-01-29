package com.bukkit.N4th4.NuxNoob;

import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.entity.Player;
import org.bukkit.Server;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginLoader;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import com.nijikokun.bukkit.Permissions.Permissions;
//import com.nijiko.permissions.PermissionHandler;
import org.bukkit.plugin.Plugin;

/**
 * NuxNoob for Bukkit
 *
 * @author N4th4
 */
public class NuxNoob extends JavaPlugin {
    private final NuxNoobPlayerListener playerListener = new NuxNoobPlayerListener(this);
    private final HashMap<Player, Boolean> debugees = new HashMap<Player, Boolean>();
    public static Permissions Permissions = null;
    private final static Logger log = Logger.getLogger("Minecraft");

    public NuxNoob(PluginLoader pluginLoader, Server instance, PluginDescriptionFile desc, File folder, File plugin, ClassLoader cLoader) {
        super(pluginLoader, instance, desc, folder, plugin, cLoader);
    }

    public void setupPermissions() {
        Plugin test = this.getServer().getPluginManager().getPlugin("Permissions");


        if(NuxNoob.Permissions == null) {
            if(test != null) {
                NuxNoob.Permissions = (Permissions)test;
                logInfo("Plug-in Permission correctement lié.");
            } else {
                logInfo("Plug-in Permission non trouvé. Désactivation.");
                this.getServer().getPluginManager().disablePlugin(this);
            }
        }
    }
    public static void logInfo(String message) {
    	log.info("[NuxNoob] "+message);
    }
    public void onEnable() {
    	setupPermissions();
    	
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_QUIT,  playerListener, Priority.Normal, this);
        pm.registerEvent(Event.Type.PLAYER_COMMAND,  playerListener, Priority.Normal, this);

        PluginDescriptionFile pdfFile = this.getDescription();
        logInfo( pdfFile.getName() + " version " + pdfFile.getVersion() + " est activé !" );
    }
    public void onDisable() {
        logInfo("Au revoir !");
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

