package com.oneshotmc.tntrecorder;

import org.bukkit.Location;

public class LocTime {
	Location location;
	long time;
	public LocTime(Location location, long time){
		this.location=location;
		this.time=time;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
}
