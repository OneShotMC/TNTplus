package com.oneshotmc.tntplus.listeners;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

import com.oneshotmc.tntplus.TNTplus;
import com.oneshotmc.tntrecorder.RecordManager;
import com.oneshotmc.tntrecorder.TntRecorder;

public class DispenseListener implements Listener {
	TNTplus plugin;
	public DispenseListener(TNTplus plugin){
		this.plugin=plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler(ignoreCancelled = false)
	public void dispenseEvent(BlockDispenseEvent e){
		if(e.getBlock().getType().equals(Material.DISPENSER)){
			if(e.getItem().getType().equals(Material.TNT)){
				TNTPrimed tnt = (TNTPrimed) e.getItem();
				Dispenser dispenser = (Dispenser) e.getBlock().getState();
				Collection<TntRecorder> RecorderSet=RecordManager.storage.getTntRecorders().values();
				for(TntRecorder rec : RecorderSet){
					if(rec.getRecording())continue;
					if(rec.getTracker(dispenser) != null)rec.getTracker(dispenser).addLoc(tnt);
					else break;
				}
			}
		}
	}
}
