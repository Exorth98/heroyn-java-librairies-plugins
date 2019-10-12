package fr.exorth.api;

import org.bukkit.Location;
import org.bukkit.Sound;

public class WorldSounds {
	
	private Location loc;
	
	public WorldSounds(Location loc){
		this.loc = loc;
	}
	
	public void playSound(Sound s){
		loc.getWorld().playSound(loc, s, 8, 8);
	}

}
