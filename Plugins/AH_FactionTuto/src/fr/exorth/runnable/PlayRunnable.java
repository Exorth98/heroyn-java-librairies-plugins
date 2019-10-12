package fr.exorth.runnable;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.FactionTuto;
import fr.exorth.util.FTUtils;

public class PlayRunnable extends BukkitRunnable  {
	
	
	
	Player p;
	ArrayList<Integer> times;
	String tutoName;
	
	ArrayList<String> points;
	FileConfiguration Tutocfg;
	
	int TimeCount=0;	
	int Point=0;


	@SuppressWarnings("unchecked")
	public PlayRunnable(String tutoname, Player p, ArrayList<Integer> times) {
		
		this.p=p;
		this.times=times;
		this.tutoName=tutoname;
		
		this.Tutocfg = FactionTuto.getInstance().getConfigManager(tutoname).getCustomConfig();
		
		this.points = (ArrayList<String>) Tutocfg.get("Points");
		
	}

	@Override
	public void run() {
		
		verifyCancel();
		
		if(TimeCount==times.get(Point)){
			
			if(TimeCount==times.get(times.size()-1)){
				end();
			}else{
				
				PlayPoint(Point);
				Point++;
			}
		}
		
		TimeCount++;
		
	}

	private void end() {
		
		p.sendMessage("§aMerci d'avoir fait ce tutoriel !");
		FTUtils.exitTuto(p);
		Bukkit.broadcastMessage("ft play gamif " + p.getName());
		FactionTuto.getInstance().getServer().dispatchCommand(FactionTuto.getInstance().getServer().getConsoleSender(), "ft play gamif " + p.getName());
		this.cancel();
		
	}

	private void verifyCancel() {
		
		if(FactionTuto.getInstance().requestCancel.contains(p)){
			
			FactionTuto.getInstance().requestCancel.remove(p);
			FTUtils.exitTuto(p);
			this.cancel();
		}
		
	}

	private void PlayPoint(int i) {
		
		String point = points.get(i);
		
		Location loc = (Location) Tutocfg.get("Locations." + point);
		
		@SuppressWarnings("unchecked")
		ArrayList<String> msg = (ArrayList<String>) Tutocfg.get("Messages." + point);
		if(msg==null){msg= (ArrayList<String>) Arrays.asList("§cErreur de chargement du message");}

		p.teleport(loc);
		FTUtils.DisplayMessage(p,msg);
		
	}
	
	

}
