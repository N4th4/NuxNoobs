package com.bukkit.N4th4.NuxNoob;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Timer;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;

/**
 * Handle events for all Player related events
 * @author N4th4
 */
public class NuxNoobPlayerListener extends PlayerListener {
    private final NuxNoob plugin;
    private final Hashtable<String, Player> playersList = new Hashtable<String, Player>();
    private final Timer timer = new Timer();
    private final ArrayList<String> noobMessage = new ArrayList<String>(); ;
    
    public NuxNoobPlayerListener(NuxNoob instance) {
        plugin = instance;
        
        String ligne = new String("");
        BufferedReader br;
		
		File test = new File("plugins/NuxNoob/");
		if (!test.exists()){
			test.mkdirs();
		}
			
		try {
			br = new BufferedReader(new FileReader("plugins/NuxNoob/message.txt"));
			while ((ligne = br.readLine()) != null)
			    noobMessage.add(ligne);
		} catch (FileNotFoundException e) {
			NuxNoob.logInfo("Vous devez créer le fichier \"plugins/NuxNoob/message.txt\"");
		} catch (IOException e) {
			NuxNoob.logInfo("Le contenu du fichier \"plugins/NuxNoob/message.txt\" n'est pas valable");
		}
	
		try {
		br = new BufferedReader(new FileReader("plugins/NuxNoob/timer.txt"));
		int time = Integer.valueOf(br.readLine()).intValue();
		timer.schedule(new Message(this), 0, time*1000);
   		} catch (FileNotFoundException e) {
   			NuxNoob.logInfo("Vous devez créer le fichier \"plugins/NuxNoob/timer.txt\"");
   		} catch (IOException e) {
   			NuxNoob.logInfo("Le fichier \"plugins/NuxNoob/timer.txt\" doit contenir un nombre de secondes");
   		}
    }

    public Hashtable<String, Player> getPlayersList() {
    	return playersList;
    }
    public ArrayList<String> getNoobMessage() {
    	return noobMessage;
    }
    public void onPlayerJoin(PlayerEvent event) {
    	Player player = event.getPlayer();
    	if (NuxNoob.Permissions.Security.getGroup(player.getName()).equals("Default")) {
        	playersList.put(player.getName(), player);
			for(int i = 0; i < noobMessage.size(); i++)
            {
				player.sendMessage(noobMessage.get(i));
            }
    	}
    }	
    public void onPlayerQuit(PlayerEvent event) {
    	Player player = event.getPlayer();
    	if (NuxNoob.Permissions.Security.getGroup(player.getName()).equals("Default")) {
    		playersList.remove(player.getName());
    	}
    }
}

