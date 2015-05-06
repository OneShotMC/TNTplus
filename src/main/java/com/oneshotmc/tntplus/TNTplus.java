package com.oneshotmc.tntplus;
import org.bukkit.plugin.java.JavaPlugin;

import com.oneshotmc.tntplus.tntfiller.TNTfiller;

public class TNTplus extends JavaPlugin{
	@Override
	public void onEnable(){
		this.getCommand("tntfill").setExecutor(new TNTfiller(this));
		getLogger().info("TNTplus has been enabled");
	}
	@Override
	public void onDisable(){
		getLogger().info("TNTplus has been disabled");
	}
}
