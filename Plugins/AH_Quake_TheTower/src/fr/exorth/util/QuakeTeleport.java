package fr.exorth.util;


import java.util.List;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.Quake;

public class QuakeTeleport {
	
	public static FileConfiguration config = Quake.getInstance().getConfig();

	
	public static void teleport(Player pl) {
		

		
		@SuppressWarnings("unchecked")
		List<Location> spawnList = (List<Location>)config.getList("arena.spawns");
		
		if(spawnList != null){
			Random r = new Random();
			int i = r.nextInt(spawnList.size()-1);

			pl.teleport(spawnList.get(i));
		}
	}
}
