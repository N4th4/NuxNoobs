package com.bukkit.N4th4.NuxNoob;

import java.util.TimerTask;
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class NNMessage extends TimerTask {
    private final NNPlayerListener listener;

    public NNMessage(NNPlayerListener parentListener) {
        listener = parentListener;
    }

    @Override
    public void run() {
        Player[] playersList = listener.plugin.getServer().getOnlinePlayers();
        for (int i=0; i<playersList.length; i++) {
            if (NNPermissions.getGroup(playersList[i].getName()).equals(listener.group)) {
                ArrayList<String> al = listener.noobMessage;
                for (int j = 0; j < al.size(); j++) {
                    playersList[i].sendMessage(al.get(i));
                }
            }
        }
    }
}
