package com.oneshotmc.tntrecorder;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RecorderStorage {
	HashMap<UUID,TntRecorder> recordings;
	public RecorderStorage(){
	}
	public void compressRecordings(){
		for(UUID ui : recordings.keySet()){
			if(!(Bukkit.getPlayer(ui).isOnline())){
				recordings.remove(ui);
			}
		}
	}
	public void addPlayer(Player player){
		UUID pu=player.getUniqueId();
		System.out.println(pu);
		recordings.put(pu, new TntRecorder(player));
	}
	public HashMap<UUID,TntRecorder> getTntRecorders (){
		return recordings;
	}
	public TntRecorder getRecorder(Player player){
		return recordings.get(player.getUniqueId());
	}
}
