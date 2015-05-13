package com.oneshotmc.tntplus.commands;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.oneshotmc.tntplus.TNTplus;
import com.oneshotmc.tntplus.tntfiller.TNTfiller;

public class TntClear implements CommandExecutor{
	@SuppressWarnings("unused")
	private TNTplus plugin;
	public TntClear(TNTplus plugin){
		this.plugin=plugin;
	}
	public TNTfiller a=new TNTfiller();
	public boolean onCommand(CommandSender sender, Command arg1, String arg2,
			String[] arg3) {
		if(!(sender instanceof Player))sender.sendMessage("Only players can do tntclear");
		if(arg1.getName().equals("tntclear")){
			Player player=(Player) sender;
			if((player.getGameMode().equals(GameMode.CREATIVE))){
				if((player.hasPermission("tntplus.tntfiller.*")
						||player.hasPermission("tntplus.*")
						||player.hasPermission("tntplus.tntfiller.tntclear")))a.clearTnt(player);
				else player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD +
						"You don't have permissions to use"+
						" TNT filler. \n"+ChatColor.BLUE + "Vote (/vote) to get it!");
				return true;
			}
			else player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+"You are not in the right gamemode!");
			return true;
		}
		return false;
	}

}
