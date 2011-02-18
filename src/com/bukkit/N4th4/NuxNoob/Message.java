package com.bukkit.N4th4.NuxNoob;

import java.util.TimerTask;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ArrayList;

import org.bukkit.entity.Player;

public class Message extends TimerTask {
	private final NuxNoobPlayerListener listener;
	
	public Message(NuxNoobPlayerListener parentListener) {
		listener = parentListener;
	}
	@Override
	public void run() {
		Hashtable<String, Player> ht = listener.playersList;
		Enumeration<Player> e = ht.elements();
		ArrayList<String> al = listener.noobMessage;
		while(e.hasMoreElements()) {
			Player player = e.nextElement();
			for(int i = 0; i < al.size(); i++)
            {
				player.sendMessage(al.get(i));
            }
		}
	}
}
