package net.heroyn.heroynserverapi.shop.Minion;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynserverapi.shop.virtualshop.VirtualMenuShopMinions;

public class ItemMenuVirtualShopMinions extends MenuItem {

	public ItemMenuVirtualShopMinions() {
		super("§6Familliers", new String[] {"§6c'est petits familliers", "§6Vous suivent partout"}, new ItemStack(Material.DRAGON_EGG));
	}
	
	@Override
	public void onUse(Player player) {
		new VirtualMenuShopMinions(player).open(player);
	}

}
