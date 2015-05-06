/**
 * @author Emerald_Explorer
 * @version 0.0.1-SNAPSHOT
 * @since 5/4/2015
 */

package com.oneshotmc.tntplus.tntfiller;
import java.util.ArrayList;
import java.util.List;

import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.oneshotmc.tntplus.TNTplus;
import com.oneshotmc.tntplus.debug.Debug;
@SuppressWarnings("unused")
public class TNTfiller implements Listener, CommandExecutor{
	//Debug mode! Don't use unless you are a developer!
	public static final boolean debug=false;
	com.oneshotmc.tntplus.TNTplus plugin;
	public TNTfiller(TNTplus plugin){
		this.plugin=plugin;
	}
	private static int radius=20;
	public static void setRadius(int r){
		radius=r;
	}
	public void fillCreative(Player player){
		int filledTnt=0;
		if(!(player.hasPermission("tntplus.tntfiller.*")||
				player.hasPermission("tntplus.tntfiller.creative")
				||player.hasPermission("tntplus.*"))){
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD +
				"You don't have permissions to use"+
				" TNT filler. \n"+ChatColor.BLUE + "Vote (/vote) to get it!");
				return;
			}
		if(debug==true){
			player.sendMessage("debug1");
		}
		ArrayList<Dispenser> dispensersFilled = new ArrayList<Dispenser>();
		if(debug==true){
			player.sendMessage("debug2");
		}
		ArrayList<Dispenser> disl = locateDispensers(player);
		if(debug==true){
			player.sendMessage("debug3");
		}
		if(debug==true){
			player.sendMessage("filling a dispenser slot");
		}
		for(Dispenser dispen : disl){
			for(int i=0;i<9;i++){
				if(debug==true){
					player.sendMessage("filling a dispenser slot (for real)");
				}
				Debug.message(player,"B1");
				try{
					if(dispen.getInventory().getItem(i).getAmount()>=64||
							!(dispen.getInventory().getItem(i).getType().equals(Material.TNT))){
							Debug.message(player,"A1");
							continue;
						}
				}
				catch(Exception e){
					Debug.message(player,"A2");
					filledTnt+=64;
					dispen.getInventory().setItem(i, new ItemStack(Material.TNT,64));
					if(!(dispensersFilled.contains(dispen))) dispensersFilled.add(dispen);	
				}
					Debug.message(player,"A3");
					filledTnt+=64-dispen.getInventory().getItem(i).getAmount();
					dispen.getInventory().setItem(i, new ItemStack(Material.TNT,64));
					if(!(dispensersFilled.contains(dispen))) dispensersFilled.add(dispen);
			}
		}
		if(disl.isEmpty()||filledTnt==0){
			player.sendMessage(ChatColor.RED+""+ ChatColor.BOLD + 
					"There are no nearby dispensers that need to be filled.");
			return;
		}
		else{
			player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD +
					"You filled " + dispensersFilled.size() + " dispensers with " + filledTnt + " tnt.");	
			return;
		}
	}
	public void fillSurvival(Player player){
		Debug.message(player,"A1");
		if(!(player.hasPermission("tntplus.tntfiller.*")||
				player.hasPermission("tntplus.tntfiller.survival")
				||player.hasPermission("tntplus.*"))){
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD +
				"You don't have permissions to use"+
				"TNT filler");
				return;
			}
		
		List<Dispenser> dispensersFilled = new ArrayList<Dispenser>();
		int filledTnt=0;
		
		int playerTnt=getInvTnt(player);
		
		if(playerTnt==0){
				player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD +
				"You don't have any" + ChatColor.ITALIC + "TNT");
				return;
			}
		ArrayList<Dispenser> disl = locateDispensers(player);
		int dispenserAverage = findDispenserAverage(disl,playerTnt).getBottom();
		Debug.message(player,"slot average:"+dispenserAverage);
		if(dispenserAverage>64)dispenserAverage=64;
		int leftOvers = findDispenserAverage(disl,playerTnt).getRemainer();
		Debug.message(player, "left overs:"+leftOvers);
		if(playerTnt<1){
			player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + 
					"You don't have any TNT!");
			return;
		}
		int sqz=0;
		/*
		 * Checks Dispensers and fills them
		 */
		for(Dispenser dispen : disl){
			Inventory inv=dispen.getInventory();
			int fullPerI=9;
			for(int i=0;i<9;i++){
				int plusOne=0;
				if(leftOvers>0)plusOne=1;
				int fillAmount=dispenserAverage+plusOne;
				ItemStack invItem=inv.getItem(i);
				if(invItem==null||invItem.getType()==null||invItem.getType().equals(Material.AIR)){
					if(fillAmount<1){
						continue;
					}
					inv.setItem(i, new ItemStack(Material.TNT,fillAmount));
					filledTnt+=fillAmount;
					if(leftOvers>0){
					leftOvers--;
				}
				}
				else if(Material.TNT==invItem.getType()&&64>invItem.getAmount()){
					if(fillAmount<1){
						continue;
					}
					int amountThere=invItem.getAmount();
					inv.setItem(i, new ItemStack(Material.TNT,fillAmount));
					filledTnt+=fillAmount-amountThere;
					if(leftOvers>0){
					leftOvers--;
					}
				}
				else{
					sqz+=filledTnt;
					fullPerI--;
					continue;
				}
			}
			if(fullPerI>0)dispensersFilled.add(dispen);
		}
		if(dispensersFilled.isEmpty()||filledTnt==0){
			player.sendMessage(ChatColor.RED+""+ ChatColor.BOLD + 
					"There are no nearby dispensers that need to be filled");
		}
		else{
			player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD +
					"You filled " + dispensersFilled.size() + " dispensers with " + filledTnt + " tnt.");	
		}
		clearInvTnt(player,filledTnt);
	}
	
	public int getInvTnt(Player player){
		int tnt = 0;
		for(int i=0;i< player.getInventory().getSize();i++){
			ItemStack item = player.getInventory().getItem(i);
			try{
			if(item.getType().equals(Material.TNT)&& item.getAmount()>=1){
				Debug.message(player, "found tnt" + tnt);
				tnt+=item.getAmount();
			}
			}
			catch(Exception e){
				continue;
			}
		}
		Debug.message(player, ""+tnt);
		return tnt;
		
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public AveragePlus findDispenserAverage(ArrayList<? extends Dispenser> list,int extraTnt){
		int totalTnt=0;
		int totalDispensers=list.size();
		for(Dispenser dis : list){
			for(int i=0;i<9;i++){
				ItemStack itemstack = dis.getInventory().getItem(i);
				if(itemstack!=null&&Material.TNT.equals(itemstack.getType())){
					totalTnt+=itemstack.getAmount();
				}
			}
		}
		//Instead of divide by nine * by nine
		Debug.message(Bukkit.getPlayer("Emerald_Explorer"), ""+totalTnt+extraTnt + "divided by "+ totalDispensers*9);
		return new AveragePlus((totalTnt+extraTnt),totalDispensers*9);
		
	}
	/**Clears a certain amount of TNT from the Player of your choice. 
	 * 
	 * @param player The player you want to remove TNT from.
	 * @param amount The amount of TNT you want to remove from the player's inventory.
	 */
	public void clearInvTnt(Player player,int amount){
		int amountLeft=amount;
		for(int i=0;i< player.getInventory().getSize();i++){
			ItemStack item = player.getInventory().getItem(i);
			if(item!=null&&item.getType().equals(Material.TNT)&& item.getAmount()>=1){
				if(amountLeft<=0){
					Debug.message(player, "3");
					continue;
					//NEVER SUPPOSED TO HAPPEN
				}
				if(amountLeft>=item.getAmount()){
					Debug.message(player, ""+item.getAmount());
					player.getInventory().clear(i);
					amount-=item.getAmount();
				}
				if(amountLeft<item.getAmount()){
					Debug.message(player, "2");
					player.getInventory().getItem(i).setAmount(item.getAmount()-amount);
					amount=0;
					break;
				}
			}
		}
	}
	public void addInvTnt(Player player, int amount){
		for(int i=0;i< player.getInventory().getSize();i++){
			ItemStack item = player.getInventory().getItem(i);
			if(item.equals(Material.TNT)&& item.getAmount()>=1){
				if(amount<=0){
					return;
					//NEVER SUPPOSED TO HAPPEN
				}
				else if(amount>=item.getAmount()){
					item.setAmount(0);
					amount-=item.getAmount();
				}
				else{
					item.setAmount(item.getAmount()-amount);
					amount=0;
					return;
				}
			}
		}
	}
	public ArrayList<Dispenser> locateDispensers(Player player){
		Location pl=player.getLocation();
		if(debug==true){
			player.sendMessage("debugA");
		}
		List<Dispenser> dispensers = new ArrayList<Dispenser>();
		for(int x=-radius;x<=radius;x++){
			if(debug==true){
				player.sendMessage("debugB");
			}
			for(int y=-radius;y<=radius;y++){
				for(int z=-radius;z<=radius;z++){
					//DebugB
					Location searcherLocation = new Location(player.getWorld(),
							pl.getBlockX()+x,pl.getBlockY()+y,pl.getBlockZ()+z);
					if(searcherLocation.getBlock().getType().
							equals(Material.DISPENSER)){
						dispensers.add((Dispenser) (searcherLocation.getBlock().getState()));
					}
				}
			}
		}
		if(debug==true){
			player.sendMessage("debugC");
		}
		return (ArrayList<Dispenser>) dispensers;
		
	}
	
	
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if(!(sender instanceof Player)){
			sender.sendMessage("Only players can do this. Sorry!");
			return true;
		}
		else if(command.getName().equals("tntfill")){
			Player player=(Player) sender;
			if(player.getGameMode().equals(GameMode.CREATIVE)){
				fillCreative(player);
				return true;
			}
			else if(player.getGameMode().equals(GameMode.SURVIVAL)||
					player.getGameMode().equals(GameMode.ADVENTURE)){
				fillSurvival(player);
				return true;
			}
			else{
				player.sendMessage(ChatColor.RED+""+ChatColor.BOLD+
						"You can't fill dispensers in that gamemode!");
			}
			return false;		
		}
		else if(command.getName().equals("tntclear")){
			
		}
		return false;
	}

}
