package com.oneshotmc.tntplus.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import com.oneshotmc.tntplus.TNTplus;
/*
 * TODO add TNT back to player when they break
 */
public class DispenserBreakListener implements Listener{
    public DispenserBreakListener(TNTplus plugin){
    	plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
	@EventHandler(ignoreCancelled = true)
	public void dispenserBreak(EntityExplodeEvent e){
		for(Block block:e.blockList()){
			if(block.getType().equals(Material.DISPENSER)){
				Dispenser dispenser=(Dispenser) block.getState();
				while(dispenser.getInventory().contains(Material.TNT))
					dispenser.getInventory().clear(
							dispenser.getInventory().first(Material.TNT)
							);
			}
			
		}
	}
	
	@EventHandler(ignoreCancelled = true)
	public void dispenserBreak(BlockBreakEvent e){
		if(e.getBlock().getType().equals(Material.DISPENSER)){
			Dispenser dispenser=(Dispenser) e.getBlock().getState();
			while(dispenser.getInventory().contains(Material.TNT))
				dispenser.getInventory().clear(
						dispenser.getInventory().first(Material.TNT)
						);
		}
	}
}