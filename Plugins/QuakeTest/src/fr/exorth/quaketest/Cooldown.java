package fr.exorth.quaketest;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Cooldown extends BukkitRunnable{
	
	private Player player;
 
    public Cooldown(Player p) {
    	player = p;
	}

	@SuppressWarnings("deprecation")
	public void run() {
        if(player.getExp() < 1F) {
            player.setExp(player.getExp() + (1F/(2*4)));  // ou 2 est le temps en sec
        } else {
            //player.setExp(0F);
        	ItemMeta meta = player.getItemInHand().getItemMeta();
        	meta.addEnchant(Enchantment.DURABILITY, 2, true);
        	meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            player.getItemInHand().setItemMeta(meta);
            player.updateInventory();
            
            Main.getInstance().ReloadingPlayers.remove(player);
            this.cancel();
        }
    }

}
