package com.oneshotmc.tntplus.debug;

import org.bukkit.entity.Player;

import com.oneshotmc.tntplus.tntfiller.TNTfiller;

public class Debug {
	private static final boolean debug=TNTfiller.debug;
	public Debug(){
		
	}
	@SuppressWarnings("unused")
	public static void message(Player player,String message){
		if(debug==true){
		player.sendMessage(message);
	}			
	}
}
