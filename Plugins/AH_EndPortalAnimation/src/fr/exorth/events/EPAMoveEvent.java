package fr.exorth.events;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import fr.exorth.EndPortalAnimation;
import fr.exorth.util.EPAUtils;

public class EPAMoveEvent implements Listener{
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if(EPAUtils.isInZone(e.getTo()) && !EPAUtils.isInZone(e.getFrom())){
			
			for(Player pl : Bukkit.getOnlinePlayers()){
				pl.hidePlayer(p);
			}
			EndPortalAnimation.getInstance().getServer().getWorld("faction12").playSound(p.getLocation(),Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
			EndPortalAnimation.getInstance().getServer().getWorld("faction12").spawnParticle(Particle.DRAGON_BREATH,p.getLocation(),50);
			
		}
		if(!EPAUtils.isInZone(e.getTo()) && EPAUtils.isInZone(e.getFrom())){
			
			for(Player pl : Bukkit.getOnlinePlayers()){
				pl.showPlayer(p);
			}
			EndPortalAnimation.getInstance().getServer().getWorld("faction12").playSound(p.getLocation(),Sound.ENTITY_ENDERMEN_TELEPORT, 1, 1);
			EndPortalAnimation.getInstance().getServer().getWorld("faction12").spawnParticle(Particle.DRAGON_BREATH,p.getLocation() ,50);
			
		}
		
	}

}
