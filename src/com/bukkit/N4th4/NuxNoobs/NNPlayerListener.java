package com.bukkit.N4th4.NuxNoobs;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

public class NNPlayerListener extends PlayerListener {
    public final NuxNoobs plugin;

    public NNPlayerListener(NuxNoobs instance) {
        plugin = instance;
    }

    public void onPlayerJoin(PlayerEvent event) {
        Player player = event.getPlayer();
        if (NNPermissions.getGroup(player.getName()).equals(plugin.group)) {
            for (int i = 0; i < plugin.noobMessage.size(); i++) {
                player.sendMessage(plugin.noobMessage.get(i));
            }
        }
    }
}