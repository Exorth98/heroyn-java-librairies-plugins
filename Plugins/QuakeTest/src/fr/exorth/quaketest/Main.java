package fr.exorth.quaketest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
//import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.BlockIterator;



public class Main extends JavaPlugin implements Listener{
	public static Main instance;
	public static Main getInstance(){
		return instance;
	}
	

	public void onEnable() {
		instance = this;
		getServer().getPluginManager().registerEvents(this, this);
		super.onEnable();
	}
	
	/*@SuppressWarnings("deprecation")
	@EventHandler
	public void onBlockPhysics(BlockPhysicsEvent e)
	{
		if (e.getBlock().getTypeId() == 39) 
		{
			e.setCancelled(true);
		}
	}*/
	
	
	ArrayList<Player> Players = new ArrayList<Player>();
	ArrayList<Player> ReloadingPlayers = new ArrayList<Player>();
	
	
    public static Entity getTarget(final Player player) {
        
        BlockIterator iterator = new BlockIterator(player.getWorld(), player
                .getLocation().toVector(), player.getEyeLocation()
                .getDirection(), 0, 100);
        Entity target = null;
        while (iterator.hasNext()) {
            Block item = iterator.next();
            for (Entity entity : player.getNearbyEntities(100, 100, 100)) {
                int acc = 2;
                for (int x = -acc; x < acc; x++)
                    for (int z = -acc; z < acc; z++)
                        for (int y = -acc; y < acc; y++)
                            if (entity.getLocation().getBlock()
                                    .getRelative(x, y, z).equals(item)) {
                                return target = entity;
                            }
            }
        }
        return target;
    }
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) 
	{
		Players.add(e.getPlayer());
	}	
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e)
	{
		Player p = e.getPlayer();
			Players.remove(p);	        
	}

	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent e)
	{
			
		//                                                                    =====RAILGUN=====
		
		if (e.getPlayer().getItemInHand().getType() == Material.DIAMOND_HOE && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) 
		{
			
			if(!ReloadingPlayers.contains(e.getPlayer())){
				
				Player p = e.getPlayer();
				
				
				/*task = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable(){
					
					int timer = 8; // (sec * 4)

					@Override
					public void run() {
						
						timer--;
						
						p.setTotalExperience(p.getTotalExperience()+1);
						
						if(timer==0){
							Main.getInstance().ReloadingPlayers.remove(p);
							Bukkit.getScheduler().cancelTask(task);
						}

					}
					
					
					
				},5,5);*/
				if(!(p.getExp() == 0F) || (!(p.getExp() == 1F))) {
					p.setExp(0F);
					ReloadingPlayers.add(p);
		        	ItemMeta meta = p.getItemInHand().getItemMeta();
		        	meta.removeEnchant(Enchantment.DURABILITY);
		            p.getItemInHand().setItemMeta(meta);
		            p.updateInventory();
					new Cooldown(p).runTaskTimer(this, 0, 5);

					
					if (p.getLocation().getY() < -2) { return; }
					
					final List<Block> blocks = p.getLineOfSight((HashSet<Byte>) null, 150);
					blocks.remove(0);
					int LaserNombreBlocks = blocks.size();
					
					HashMap<Location, String> pLoc = new HashMap<Location, String>();				
					
					
					for (Player ply : Players) 
					{
						
						if (ply != p) 
						{
							pLoc.put(new Location(ply.getWorld(), ply.getLocation().getBlockX(), ply.getLocation().getBlockY(), ply.getLocation().getBlockZ()), ply.getName());
						}
						
					}
					
					
					

					
					for (int i = LaserNombreBlocks; i > 0 ; i--)
					{
						
						
						
						
						if (blocks.get((i-1)).getTypeId() == 0)
						{
							//blocks.get((i-1)).setTypeId(89);
							
							
							//blocks.get((i-1)).getWorld().playEffect(blocks.get((i-1)).getLocation(), Effect.FLAME, 1);
							blocks.get((i-i)).getWorld().spawnParticle(Particle.SMOKE_NORMAL,blocks.get((i-1)).getLocation() , 1,0.0,0.0,0.0);
							blocks.get((i-i)).getWorld().spawnParticle(Particle.FIREWORKS_SPARK,blocks.get((i-1)).getLocation() , 1,0.0,0.0,0.0);
							
							//If the laser hit the legs
							if (pLoc.containsKey(blocks.get((i-1)).getLocation()))
							{
								String PlayerToKill = pLoc.get(blocks.get((i-1)).getLocation());
								getServer().getPlayer(PlayerToKill).setHealth(0);

								
							}
							//If the laser hit the body/head
							else if (pLoc.containsKey(  new Location(blocks.get((i-1)).getWorld(), blocks.get((i-1)).getX(), blocks.get((i-1)).getY() - 1, blocks.get((i-1)).getZ())  ))
							{
								String PlayerToKill = pLoc.get(new Location(blocks.get((i-1)).getWorld(), blocks.get((i-1)).getX(), blocks.get((i-1)).getY() - 1, blocks.get((i-1)).getZ()));
									getServer().getPlayer(PlayerToKill).setHealth(0);
									
			
							}
						}
						else 
						{
							blocks.remove(i-1);
						}

					}
					
					getServer().getWorld("world").playSound(p.getLocation(), Sound.ENTITY_WITHER_HURT, 1, 1);
					
					
					/*getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
					{
						public void run()
						{
							for (Block b : blocks)
							{
								b.setTypeId(0);
							}
						}
						
					}, 10L);*/

		
				}

			}
	
		}
		
	}
	
}
