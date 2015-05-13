package com.oneshotmc.tntplus.listeners;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.oneshotmc.tntrecorder.RecordManager;

public class PlayerClick implements Listener{
	@EventHandler
	public void playerClickEvent(PlayerInteractEvent e){
		Player player=e.getPlayer();
		if(e.getAction()==
				org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK&&
				e.getClickedBlock().getType().equals(Material.DISPENSER)){
			if(player.getItemInHand()
					.getType().equals(Material.WOOD_SWORD)){
				RecordManager.storage.getRecorder(player)
				.addDispenser((Dispenser) e.getClickedBlock());
			}
			
		}
		else if(e.getAction()==org.bukkit.event.block.Action.LEFT_CLICK_BLOCK&&
				e.getClickedBlock().getType().equals(Material.DISPENSER)){
			if(player.getItemInHand()
					.getType().equals(Material.WOOD_SWORD)){
				RecordManager.storage.getRecorder(player)
				//remove
				.removeDispenser((Dispenser) e.getClickedBlock());
			}
		}
	}
}
