package net.heroyn.heroynserverapi.SpecialItem.Item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;

public class ItemMenuAnnulerShop 
	extends MenuItem
{

	public ItemMenuAnnulerShop() {
		super("§cAnnuler", new String[0], new ItemStack(Material.MAGMA_CREAM));
	}

	@Override
	public void onUse(Player player) {
		player.sendMessage("§7[§bBoutique§7] §cAchat annuler !");
		player.closeInventory();
	}
	
	

}
