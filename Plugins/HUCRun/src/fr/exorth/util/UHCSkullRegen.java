package fr.exorth.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class UHCSkullRegen implements Listener{
	
	public static void dropSkull(Player victim){
		
		ItemStack skullvictim = new ItemStack(Material.SKULL_ITEM,1,(byte)3);
		SkullMeta skullM = (SkullMeta) skullvictim.getItemMeta();
		
		skullM.setOwner(victim.getName());
		
		victim.getWorld().dropItemNaturally(victim.getLocation(), skullvictim);
		
	}
	
	@EventHandler
	public void skullClick(PlayerInteractEvent e){
		
		Player p = e.getPlayer();
		Material mat = e.getMaterial();
		
		if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
			
			if(mat != null){
				if(mat == Material.SKULL_ITEM){
					p.getInventory().remove(e.getItem());
					p.updateInventory();
					
					p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION,200,2));
				}
			}
		}
	}

}
