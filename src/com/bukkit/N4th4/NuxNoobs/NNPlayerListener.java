package com.bukkit.N4th4.NuxNoobs;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;

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
                player.sendMessage("[NuxNoobs] Donnez au moins un argument");
            } else if (command[1].equalsIgnoreCase("reload")) {
                if (NNPermissions.has(player, "nuxnoob.reload")) {
                    loadConfig();
                    player.sendMessage("[NuxNoobs] Fichier rechargé");
                    player.sendMessage("[NuxNoobs] Groupe : " + group);
                    player.sendMessage("[NuxNoobs] Timer : " + time + " secondes");
                    player.sendMessage("[NuxNoobs] Message :");
                    for (int i = 0; i < noobMessage.size(); i++) {
                        player.sendMessage(noobMessage.get(i));
                    }
                } else {
                    player.sendMessage("[NuxNoobs] Permission refusée");
                }
            } else {
                player.sendMessage("[NuxNoobs] Commande inconnue");
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
                NNLogger.severe("Le timer doit être plus grand que 0");
            }
        } else {
            NNLogger.severe("Fichier de configuration non trouvé : plugins/NuxNoobs/config.yml");
        }
    }
}
