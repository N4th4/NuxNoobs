package com.bukkit.N4th4.NuxNoob;

import java.io.File;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Timer;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.config.Configuration;

public class NNPlayerListener extends PlayerListener {
    private final NuxNoob plugin;
    private Timer timer;
    private int time;
    public final Hashtable<String, Player> playersList;
    public ArrayList<String> noobMessage;

    public NNPlayerListener(NuxNoob instance) {
        plugin = instance;
        playersList = new Hashtable<String, Player>();
        timer = new Timer();
        noobMessage = new ArrayList<String>();
        loadConfig();
    }

    public void onPlayerJoin(PlayerEvent event) {
        Player player = event.getPlayer();
        if (NNPermissions.getGroup(player.getName()).equals("Default")) {
            playersList.put(player.getName(), player);
            for (int i = 0; i < noobMessage.size(); i++) {
                player.sendMessage(noobMessage.get(i));
            }
        }
    }

    public void onPlayerQuit(PlayerEvent event) {
        Player player = event.getPlayer();
        if (NNPermissions.getGroup(player.getName()).equals("Default")) {
            playersList.remove(player.getName());
        }
    }

    public void onPlayerCommand(PlayerChatEvent event) {
        String[] command = event.getMessage().split(" ");
        if (command[0].equalsIgnoreCase("/NuxNoob")) {
            Player player = event.getPlayer();
            if (command.length == 1) {
                player.sendMessage("[NuxNoob] Donnez au moins un argument");
            } else if (command[1].equalsIgnoreCase("reload")) {
                if (NNPermissions.has(player, "nuxnoob.reload")) {
                    loadConfig();
                    player.sendMessage("[NuxNoob] Fichier rechargé");
                    player.sendMessage("[NuxNoob] Timer : " + time + " secondes");
                    player.sendMessage("[NuxNoob] Message :");
                    for (int i = 0; i < noobMessage.size(); i++) {
                        player.sendMessage(noobMessage.get(i));
                    }
                } else {
                    player.sendMessage("[NuxNoob] Permission refusée");
                }
            } else {
                player.sendMessage("[NuxNoob] Commande inconnue");
            }
            event.setCancelled(true);
        }
    }

    private void loadConfig() {
        File configFile = new File(plugin.getDataFolder() + "/config.yml");
        if (configFile.exists()) {
            Configuration config = new Configuration(configFile);
            config.load();
            time = config.getInt("timer", 0);
            noobMessage = (ArrayList<String>) config.getStringList("message", noobMessage);
            if (time != 0) {
                timer.cancel();
                timer = new Timer();
                timer.schedule(new NNMessage(this), 0, time * 1000);
            } else {
                NNLogger.severe("Le timer doit être plus grand que 0");
            }
        } else {
            NNLogger.severe("Fichier de configuration non trouvé : " + plugin.getDataFolder() + "/config.yml");
        }
    }
}