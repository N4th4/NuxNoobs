package com.bukkit.N4th4.NuxNoobs;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.util.config.Configuration;

public class NuxNoobs extends JavaPlugin {
    private final NNPlayerListener playerListener;
    private final HashMap<Player, Boolean> debugees;
    private Timer timer;
    private int time;
    public String group;
    public ArrayList<String> noobMessage;

    public NuxNoobs() {
        NNLogger.initialize();
        playerListener = new NNPlayerListener(this);
        debugees = new HashMap<Player, Boolean>();

        timer = new Timer();
        noobMessage = new ArrayList<String>();
    }

    public void onEnable() {
        NNPermissions.initialize(getServer());

        loadConfig();

        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.PLAYER_JOIN, playerListener, Priority.Normal, this);
    }

    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        String commandName = command.getName();
        if (sender instanceof Player) {
            if (commandName.equalsIgnoreCase("NuxNoobs")) {
                Player player = (Player) sender;
                if (args.length == 0) {
                    player.sendMessage(ChatColor.RED + "[NuxNoobs] Give at least one argument");
                } else if (args[0].equalsIgnoreCase("reload")) {
                    if (NNPermissions.has(player, "nuxnoob.reload")) {
                        loadConfig();
                        player.sendMessage(ChatColor.GREEN + "[NuxNoobs] File reloaded");
                        player.sendMessage(ChatColor.GREEN + "[NuxNoobs] Group : " + group);
                        player.sendMessage(ChatColor.GREEN + "[NuxNoobs] Timer : " + time + " secondes");
                        player.sendMessage(ChatColor.GREEN + "[NuxNoobs] Message :");
                        for (int i = 0; i < noobMessage.size(); i++) {
                            player.sendMessage("    " + ChatColor.GREEN + noobMessage.get(i));
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "[NuxNoobs] Permission denied");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "[NuxNoobs] Unknown command");
                }
            }
            return true;
        } else {
            sender.sendMessage("[NuxNoob] Only commands in chat are supported");
            return true;
        }
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

    private void loadConfig() {
        File configFile = new File("plugins/NuxNoobs/config.yml");
        if (configFile.exists()) {
            Configuration config = new Configuration(configFile);
            config.load();
            time = config.getInt("timer", 0);
            noobMessage = (ArrayList<String>) config.getStringList("message", noobMessage);
            group = config.getString("group", "Default");
            if (time != 0) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new NNMessage(this), 0, time * 1000);
            } else {
                NNLogger.severe("The timer must be bigger than 0");
            }
        } else {
            NNLogger.severe("File not found : plugins/NuxNoobs/config.yml");
        }
    }
}
