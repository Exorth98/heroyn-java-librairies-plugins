package fr.exorth.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.EndPortalAnimation;
import fr.exorth.util.EPAUtils;

public class EPARunnable extends BukkitRunnable{
	
	static Location center = new Location(Bukkit.getWorld("faction12"), -1032, 35, 479);
	
	int timer =0;

	@Override
	public void run() {
		
		for(Player p : Bukkit.getOnlinePlayers()){
			
			if(EPAUtils.isInZone(p.getLocation())){
				
				if(!EPAUtils.isLookingHole(p) && !EPAUtils.isPitchOk(p)){
					p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,30,2),true);
				}
			
				if(timer==15){
					EndPortalAnimation.getInstance().getServer().getWorld("faction12").playSound(center,Sound.ENTITY_ENDERDRAGON_GROWL, 50F, 50F);

				}
				if(timer==30){
					EndPortalAnimation.getInstance().getServer().getWorld("faction12").playSound(center, Sound.ENTITY_ENDERDRAGON_FLAP, 50F, 50F);

				}
				
			}
			
		}
		
		if(timer==30){
			timer=0;
		}
		
		timer++;
		
	}

}
