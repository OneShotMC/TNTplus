package com.oneshotmc.tntrecorder;

import java.util.HashMap;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;

import com.oneshotmc.tntplus.TNTplus;


public class TntRecorder {
	public static TNTplus plugin;
	public TntRecorder(){}
	//This might not be a good practice v
	public static void setMainPlugin(TNTplus plugin1){
		plugin=plugin1;
	}
	private boolean recording=false;
	int maxDispensers=10;
	int maxRecordSeconds=30;
	long timestart;
	UUID uuid;
	HashMap<Dispenser,TntTracker> trackers; 
	public TntRecorder(Player player){
		System.out.println(player.getName());
		this.uuid=player.getUniqueId();
	}
	public void setRecording(boolean b){
		recording=b;
		if(b==true){
			timestart=System.currentTimeMillis();
			for(TntTracker track : trackers.values()){
			track.startRecording(false);
			}
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable(){
				public void run(){
					Bukkit.getPlayer(uuid).sendMessage(ChatColor.YELLOW+""+ChatColor.BOLD+"You have reached your max recording time. Your recording"+
				"has been ended.");
					setRecording(false);
				}
			}, maxRecordSeconds*20);
		}
		else
			for(TntTracker track : trackers.values()){
				track.stopRecording();
				}
	}
	public void removeDispenser(Dispenser dispenser){
		Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Successfully removed dispenser from the recording list.");
		trackers.remove(dispenser);
	}
	public boolean getRecording(){
		return recording;
	}
	public void addDispenser(Dispenser dispenser){
		if(trackers.containsKey(dispenser)){
			System.out.println("Already has dispenser key!");
			return;
		}
		if(trackers.size()<=10){
			Bukkit.getPlayer(uuid).sendMessage(ChatColor.GREEN+""+ChatColor.BOLD+"Added dispenser recorder.");
			trackers.put(dispenser, new TntTracker(dispenser));
		}
		else{
			Bukkit.getPlayer(uuid).sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You already have reached your max number of dispenser trackers!"
		+ChatColor.RESET+""+ChatColor.YELLOW+"Left click with your wooden sword to remove previous ones!");
		}
	}
	public HashMap<Dispenser,TntTracker> getTrackerMap(){
		return trackers;
	}
	public TntTracker getTracker(Dispenser dispenser){
		return trackers.get(dispenser);
	}
	
}
