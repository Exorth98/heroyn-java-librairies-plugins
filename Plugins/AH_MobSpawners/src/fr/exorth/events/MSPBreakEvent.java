package fr.exorth.events;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.MobSpawners;
import fr.exorth.util.ItemUtil;
import fr.exorth.util.MSPUtils;

public class MSPBreakEvent implements Listener {
	
	HashMap<Location,EntityType> Boss = new HashMap<>();
	
	@EventHandler
	public void onBreak(BlockBreakEvent e){
			
		@SuppressWarnings("deprecation")
		ItemStack inHand = e.getPlayer().getItemInHand();
		
		if(inHand != null){
			
			if(inHand.getType() == Material.DIAMOND_PICKAXE){
				if(inHand.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)){

					if(e.getBlock().getType()== Material.MOB_SPAWNER){
											
						String ref = inHand.getItemMeta().getLore().get(inHand.getItemMeta().getLore().size()-1).split(":")[1];
						MSPUtils.removeref(ref);
						e.getPlayer().getInventory().removeItem(inHand);
						e.setCancelled(true);
						
						
						CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
						EntityType eType = spawner.getSpawnedType();
						ItemStack mobSpawner = new ItemStack(Material.MOB_SPAWNER,1);
						mobSpawner = ItemUtil.addLore(mobSpawner, "§cType:" + eType.name());
						
						e.getBlock().setType(Material.AIR);
						e.getBlock().getWorld().dropItemNaturally(e.getBlock().getLocation(), mobSpawner);	
						

						
						e.getPlayer().sendMessage("§bLe §cSpawnerBoss §bva spawn dans 5 sec");
						e.getPlayer().sendMessage("§bIl est très puissant mais loot beaucoup");
						e.getPlayer().sendMessage("§bCours si tu ne veux pas l'affronter !");
						e.getPlayer().sendMessage("§bSinon tiens toi prêt...");
						
						new BukkitRunnable(){

							@Override
							public void run() {
																
								Boss.put(e.getBlock().getLocation(), eType);
								e.getBlock().getWorld().spawnEntity(e.getBlock().getLocation(), eType);
								
							}						
						}.runTaskLater(MobSpawners.getInstance(), 100);

						
						
					}else{
						e.setCancelled(true);
					}					
				}							
			}
		}	
	}
	
	@EventHandler
	public void onEntitySpawn(EntitySpawnEvent e){
		
		if(Boss.containsKey(e.getLocation())){
			if(Boss.get(e.getLocation()).equals(e.getEntityType())){
				
				Boss.remove(e.getLocation());
				LivingEntity ent = (LivingEntity) e.getEntity();
				
				if(e.getEntityType()!=EntityType.SPIDER && e.getEntityType()!=EntityType.CAVE_SPIDER){
					
					ent.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,Integer.MAX_VALUE,3));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,Integer.MAX_VALUE,4));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,Integer.MAX_VALUE,9));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,Integer.MAX_VALUE,9));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,4));
					
				}else{
					ent.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,Integer.MAX_VALUE,3));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST,Integer.MAX_VALUE,9));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE,Integer.MAX_VALUE,9));
					ent.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,Integer.MAX_VALUE,4));				
				}
				
				e.getLocation().getWorld().playSound(e.getLocation(), Sound.ENTITY_LIGHTNING_THUNDER,35F,50F);
				
				e.getLocation().getWorld().spawnEntity(e.getLocation(), e.getEntityType());
				e.getLocation().getWorld().spawnEntity(e.getLocation(), e.getEntityType());
				e.getLocation().getWorld().spawnEntity(e.getLocation(), e.getEntityType());
				
				ent.setCustomName("§c§lSpawner Boss");
				ent.setCustomNameVisible(true);
				
				ent.setHealth(50);

				
			}
			
		}
		
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e){
		
		if(e.getBlock().getType() == Material.MOB_SPAWNER){
			
			@SuppressWarnings("deprecation")
			ItemStack inHand = e.getPlayer().getItemInHand();
			
			if(inHand != null){
				
				if(inHand.getType() == Material.MOB_SPAWNER){
					
					if(inHand.getItemMeta().getLore() != null){
						
						if(inHand.getItemMeta().getLore().get(inHand.getItemMeta().getLore().size()-1) != null){
							
							String LastLore = inHand.getItemMeta().getLore().get(inHand.getItemMeta().getLore().size()-1);
							
							if(LastLore.contains("Type:")){
								
								String TypeName = LastLore.split(":")[1];
								
								
								@SuppressWarnings("deprecation")
								EntityType eType = EntityType.fromName(TypeName);
								
								CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
								spawner.setSpawnedType(eType);
								
							}															
						}						
					}										
				}			
			}			
		}	
	}
	
	@EventHandler
	public void onEntityDeath(EntityDeathEvent e){
		
		if(e.getEntity().isCustomNameVisible()){
			
			if(e.getEntity().getCustomName().contains("§c§lSpawner Boss")){
				
				e.setDroppedExp(5000);
				
				
				
			}
			
		}
		

		
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e){
		
		if(e.getEntity().isCustomNameVisible()){
			
			if(e.getEntity().getCustomName().contains("§c§lSpawner Boss")){
				
				e.setDamage(e.getDamage()/3);			

				e.getEntity().setCustomName("§c§lSpawner Boss §r" + getLife(e.getEntity()));
			}
			
		}
		

		
	}
	
	private String getLife(Entity entity) {
		
		LivingEntity ent = (LivingEntity) entity;
		
		String life = "§a";
		
		for(int i =0;i<((ent.getHealth()*15)/50);i++){
			life += "|";
		}
		
		life += "§c";
		
		for(int i =life.length();i<=19;i++){
			life += "|";
		}
		
		
		return life;
	}

	int absorpHearts(LivingEntity p)
	{
	    for (PotionEffect pe : p.getActivePotionEffects())
	    {
	        if (pe.getType().equals(PotionEffectType.ABSORPTION))
	        {
	            int amt = pe.getAmplifier() * 2 + 2;
	            return amt;
	        }
	    }
	    return 0;
	}
	
	
	
}
