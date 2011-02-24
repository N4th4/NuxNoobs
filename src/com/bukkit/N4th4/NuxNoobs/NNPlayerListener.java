package com.bukkit.N4th4.NuxNoobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.config.Configuration;

public class NNPlayerListener extends PlayerListener {
    public final NuxNoobs plugin;
    private Timer timer;
    private int time;
    public String group;
    public ArrayList<String> noobMessage;

    public NNPlayerListener(NuxNoobs instance) {
        plugin = instance;
        timer = new Timer();
        noobMessage = new ArrayList<String>();
        loadConfig();
    }

    public void onPlayerJoin(PlayerEvent event) {
        Player player = event.getPlayer();
        if (NNPermissions.getGroup(player.getName()).equals(group)) {
            for (int i = 0; i < noobMessage.size(); i++) {
                player.sendMessage(noobMessage.get(i));
            }
        }
    }

    public void onPlayerCommand(PlayerChatEvent event) {
        String[] command = event.getMessage().split(" ");
        if (command[0].equalsIgnoreCase("/NuxNoobs")) {
            Player player = event.getPlayer();
            if (command.length == 1) {
                player.sendMessage(ChatColor.RED + "[NuxNoobs] Give at least one argument");
            } else if (command[1].equalsIgnoreCase("reload")) {
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
            event.setCancelled(true);
        }
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
