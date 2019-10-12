package fr.exorth.util;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class UHCTeleport {
	
	public static void tpRandom(Player p){
		
		
		Random r = new Random();
		int x = r.nextInt(1000);
		int y = 250;
		int z = - r.nextInt(1000);
		
		World world = p.getWorld();
		
		Location randomLoc = new Location(world,x,y,z);
		
		p.teleport(randomLoc);
	}

}
