package com.oneshotmc.tntrecorder;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class RecordManager {
	public static RecorderStorage storage = new RecorderStorage();
	public static void addNessassaryPlayers(){
		for(Player player : Bukkit.getOnlinePlayers()){
			storage.addPlayer(player);
		}
	}
}
