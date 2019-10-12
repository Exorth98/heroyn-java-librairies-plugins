package fr.exorth.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import fr.exorth.Quake;
import fr.exorth.game.QuakeState;
import fr.exorth.util.QuakeReload;
import fr.exorth.util.QuakeShotUtil;

public class QuakeShot implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
			
		//                                                                    =====RAILGUN=====
		
		for(Entry<Player,Material> m : Quake.getInstance().hoes.entrySet()){
			Material mat = m.getValue();
			
			if ( QuakeState.isState(QuakeState.GAME) && e.getPlayer().getItemInHand().getType() == mat && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) 
			{
				if(!Quake.getInstance().ReloadingPlayers.contains(e.getPlayer())){
					Player p = e.getPlayer();
					
					if(!(p.getExp() == 0F) || (!(p.getExp() == 1F))) {
						p.setExp(0F);
						Quake.getInstance().ReloadingPlayers.add(p);
						new QuakeReload(p,mat).runTaskTimer(Quake.getInstance(), 0, 5);

						
						if (p.getLocation().getY() < -2) { return; }
						
						final List<Block> blocks = p.getLineOfSight((HashSet<Byte>) null, 150);
						//blocks.remove(0);
						int LaserNombreBlocks = blocks.size();
						
						HashMap<Player, Location> pLoc = new HashMap<Player,Location >();				
						
						
						for (UUID uuid: Quake.getInstance().playerInGame) 
						{
							
							if (uuid != p.getUniqueId()) 
							{
								Player ply = Bukkit.getPlayer(uuid);
								pLoc.put(ply,new Location(ply.getWorld(), ply.getLocation().getBlockX(), ply.getLocation().getBlockY(), ply.getLocation().getBlockZ()));
							}
							
						}
						
						
						ArrayList <Player> checked = new ArrayList <>();

						int killstreak=0;
						
						for (int i = LaserNombreBlocks-1; i > 0 ; i--)
						{
							
							
							if (blocks.get(i).getTypeId() == 0)
							{

								blocks.get(i).getWorld().spawnParticle(Particle.SMOKE_NORMAL,blocks.get(i).getLocation() , 1,0.0,0.0,0.0);
								blocks.get(i).getWorld().spawnParticle(Particle.FIREWORKS_SPARK,blocks.get(i).getLocation() , 1,0.0,0.0,0.0);
								
								
														
								
								for(Player pl : pLoc.keySet()){
									Block blockp =pLoc.get(pl).getBlock();			
									
					                int acc = 1;
					                for (int x = -acc; x < acc ; x++)
					                    for (int z = -acc; z < acc; z++)
					                        for (int y = -acc; y < acc; y++)
					                            if (blocks.get(i).getRelative(x, y, z).equals(blockp)) {
					                            	
					                            	if(!checked.contains(pl)){
					                            		
														Player Victim = pl;
														Player Killer = p;
														
														
														QuakeShotUtil.doShot(Victim,Killer);
														
														killstreak++;
														
														
														checked.add( pl);
					                            	}

														
					                                
					                            }
								}

							}
							else 
							{
								blocks.remove(i);
							}

						}
						QuakeShotUtil.killstreak(p,killstreak);
						QuakeShotUtil.checkVictory();	
						
						Quake.getInstance().getServer().getWorld("world").playSound(p.getLocation(), Sound.ENTITY_WITHER_HURT, 1, 1);
						
					}

				}
		
			}
			
		}
		

		
	}

}
