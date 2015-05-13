package com.oneshotmc.tntrecorder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.block.Dispenser;
import org.bukkit.entity.TNTPrimed;

public class TntTracker {
	@SuppressWarnings("unused")
	private boolean recording;
	private long maxMilliseconds=30000;
	private long timestart;
	Dispenser dispenser;
	private HashMap<TNTPrimed,List<LocTime>> tntlocs;
	public TntTracker(Dispenser dispenser){
		this.dispenser=dispenser;
	}
	public void startRecording(boolean autostop){
		recording=true;
		timestart=System.currentTimeMillis();
		if(autostop==true){
			while(true){
				if(System.currentTimeMillis()-timestart<maxMilliseconds){
					stopRecording();
					break;
				}
			}
		}
		
	}
	public void stopRecording(){
		recording=false;
	}
	public Dispenser getDispenser(){
		return dispenser;
	}
	public void setDispenser(Dispenser dispenser){
		this.dispenser=dispenser;
	}
	public void addLoc(TNTPrimed tnt){
		List<LocTime> time = tntlocs.get(tnt);
		if(time==null){
			tntlocs.put(tnt,Arrays.asList(new LocTime(tnt.getLocation(), System.currentTimeMillis()-timestart)));	
		}
		else{
			time.add(new LocTime(tnt.getLocation(), System.currentTimeMillis()-timestart));
			//Is this a bad practice? v
			tntlocs.put(tnt, time);
		}
	}
	public HashMap<TNTPrimed,List<LocTime>> getLocs(){
		return tntlocs;
	}
	public void clearLocs(){
		tntlocs.clear();
	}
}
