package com.oneshotmc.tntrecorder;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class TntRecorder {
	List<Location> tracker = new ArrayList<Location>();
	Location location;
	public TntRecorder(Location location){
		this.location=location;
	}
}
