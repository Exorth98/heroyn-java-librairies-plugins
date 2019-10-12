package fr.exorth.events;


import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.exorth.Elevation;
import fr.exorth.runnable.DownOpenAnimation;
import fr.exorth.runnable.UpOpenAnimation;
import fr.exorth.util.ElevationUtils;

public class MoveEvent implements Listener {
	
	
	@EventHandler
	public void onMove(PlayerMoveEvent e){
		
		Player p = e.getPlayer();
		
		if(ElevationUtils.isInZone(p.getLocation()) || ElevationUtils.isInSecondZone(p.getLocation())){
			
			if(!Elevation.getInstance().onDown.contains(p)){
				
				if(ElevationUtils.isInZone(p.getLocation())){
					
					
					if(ElevationUtils.isAtUp(p.getLocation())){
						
						p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,10,55), true);
						
					}else{
						p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,5,20), true);
					}
					
				}

				
				
				Location ploc = p.getLocation();
				Location OverBlock = ploc.clone().add(0.0,2.0,0.0);
				
				for (int i=0;i<12;i++){
										
					if( OverBlock.getBlock().getType()== Material.STAINED_GLASS &&(ElevationUtils.isInZone(OverBlock) || ElevationUtils.isInSecondZone(OverBlock))){
						
						new UpOpenAnimation(OverBlock).runTaskTimer(Elevation.getInstance(), 0, 5);
						
					}
					OverBlock.add(0,1,0);
					
				}

			}
			
			
			/*if(ElevationUtils.isInSecondZone(p.getLocation()) && Elevation.getInstance().onDown.contains(p)){
				
				Location ploc = p.getLocation();
				Location UnderBlock = ploc.clone().subtract(0.0,1.0,0.0);
				
				for (int i=0;i<10;i++){
										
					if(UnderBlock.getBlock().getType()== Material.STAINED_GLASS && (ElevationUtils.isInZone(UnderBlock))){
						
						new DownOpenAnimation(UnderBlock,p,10).runTaskTimer(Elevation.getInstance(), 0, 10);
						
					}
					UnderBlock.subtract(0,1,0);
					
				}
				
			}*/
			
			if(ElevationUtils.isInSecondZone(p.getLocation()) && e.getFrom().getBlockY()==e.getTo().getBlockY()-1 && ElevationUtils.isJumpOk(e.getFrom())){
				
				Elevation.getInstance().onDown.remove(p);
				p.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION,10,60), true);
				
			}
			

		}
		
		if(ElevationUtils.isInZone(p.getLocation().clone().subtract(0,1,0))){
			if(e.getFrom().getBlockY() - e.getTo().getBlockY()>0){
				if(!Elevation.getInstance().onDown.contains(p)){
					e.setCancelled(true);
				}
			}
		}

		
		
		
	}
	
	@EventHandler
	public void onSneak(PlayerToggleSneakEvent e){
		
		Player p = e.getPlayer();
		
		if(ElevationUtils.isInZone(p.getLocation().clone().subtract(0,1,0)) && !ElevationUtils.isInZone(p.getLocation())){		
			
				Elevation.getInstance().onDown.add(p);
				
				Location ploc = p.getLocation();
				Location UnderBlock = ploc.clone().subtract(0,1,0);
				
				new DownOpenAnimation(UnderBlock,p,10).runTaskTimer(Elevation.getInstance(),0,10);
				

			
		}
		
		if(ElevationUtils.isInSecondZone(p.getLocation().clone().subtract(0,1,0)) && !ElevationUtils.isInSecondZone(p.getLocation())){		
			
				Elevation.getInstance().onDown.add(p);
				
				Location ploc = p.getLocation();
				Location UnderBlock = ploc.clone().subtract(0,1,0);
				
				new DownOpenAnimation(UnderBlock,p,4).runTaskTimer(Elevation.getInstance(),0,10);
				

			
		}
		
		/*if(ElevationUtils.isInSecondZone(p.getLocation()) && !Elevation.getInstance().onDown.contains(p)){
			
		}*/
		
	}

}
