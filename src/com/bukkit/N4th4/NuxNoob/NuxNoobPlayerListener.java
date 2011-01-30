package com.bukkit.N4th4.NuxNoob;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.Hashtable;
import java.util.ArrayList;
import java.util.Timer;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;

/**
 * Handle events for all Player related events
 * @author N4th4
 */
public class NuxNoobPlayerListener extends PlayerListener {
    //private final NuxNoob plugin;
    private final Hashtable<String, Player> playersList;
    private final ArrayList<String> noobMessage;
    private Timer timer;
    private int time;
    
    public NuxNoobPlayerListener(NuxNoob instance) {
        //plugin = instance;
    	playersList = new Hashtable<String, Player>();
    	timer = new Timer();
    	noobMessage = new ArrayList<String>();
    	loadFiles();
    }
    /*
     * Envoie le message au joueur puis l'ajoute à la liste
     * @see org.bukkit.event.player.PlayerListener#onPlayerJoin(org.bukkit.event.player.PlayerEvent)
     */
    public void onPlayerJoin(PlayerEvent event) {
    	Player player = event.getPlayer();
    	if (NuxNoobPermissions.getGroup(player.getName()).equals("Default")) {
        	playersList.put(player.getName(), player);
			for(int i = 0; i < noobMessage.size(); i++)
            {
				player.sendMessage(noobMessage.get(i));
            }
    	}
    }
    /*
     * Supprime le joueur de la liste
     * @see org.bukkit.event.player.PlayerListener#onPlayerQuit(org.bukkit.event.player.PlayerEvent)
     */
    public void onPlayerQuit(PlayerEvent event) {
    	Player player = event.getPlayer();
    	if (NuxNoobPermissions.getGroup(player.getName()).equals("Default")) {
    		playersList.remove(player.getName());
    	}
    }
    /*
     * Traite les commandes 
     */
    public void onPlayerCommand(PlayerChatEvent event) {
    	String[] command = event.getMessage().split(" ");
    	if (command[0].equalsIgnoreCase("/NuxNoob")) {
    		Player player = event.getPlayer();
    		if (command.length == 1) {
    			player.sendMessage("[NuxNoob] Donnez au moins un argument");
    		} else if (command[1].equalsIgnoreCase("reload")) {
    			if (NuxNoobPermissions.has(player, "nuxnoob.reload")) {
    				loadFiles();
        			player.sendMessage("[NuxNoob] Fichiers rechargés");
       				player.sendMessage("[NuxNoob] Timer : "+time+" secondes");
       				player.sendMessage("[NuxNoob] Message :");
       				for(int i = 0; i < noobMessage.size(); i++)
      				{
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
    /*
     * Recharges les fichier de conf' quand demandé
     */
    private void loadFiles() {
    	String ligne = new String("");
    	BufferedReader br;
    	noobMessage.clear();
    	timer.cancel();
    	timer = new Timer();
	
    	File test = new File("plugins/NuxNoob/");
    	if (!test.exists()){
    		test.mkdirs();
    	}
    	
    	try {
    		br = new BufferedReader(new FileReader("plugins/NuxNoob/message.txt"));
    		while ((ligne = br.readLine()) != null)
    			noobMessage.add(ligne);
    	} catch (FileNotFoundException e) {
    		NuxNoobLogger.severe("Vous devez créer le fichier \"plugins/NuxNoob/message.txt\"");
    	} catch (IOException e) {
    		NuxNoobLogger.severe("Le contenu du fichier \"plugins/NuxNoob/message.txt\" n'est pas valable");
    	}
    	
    	try {
    		br = new BufferedReader(new FileReader("plugins/NuxNoob/timer.txt"));
    		time = Integer.valueOf(br.readLine()).intValue();
    		timer.schedule(new Message(this), 0, time*1000);
    	} catch (FileNotFoundException e) {
    		NuxNoobLogger.severe("Vous devez créer le fichier \"plugins/NuxNoob/timer.txt\"");
    	} catch (IOException e) {
    		NuxNoobLogger.severe("Le fichier \"plugins/NuxNoob/timer.txt\" doit contenir un nombre de secondes");
    	}
    }
	//		GETTERS			//
    public Hashtable<String, Player> getPlayersList() {
    	return playersList;
    }
    public ArrayList<String> getNoobMessage() {
    	return noobMessage;
    }
}

