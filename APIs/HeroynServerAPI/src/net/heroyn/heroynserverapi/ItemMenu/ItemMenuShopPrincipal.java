package net.heroyn.heroynserverapi.ItemMenu;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynserverapi.shop.VirtualMenuShopPrincipal;

public class ItemMenuShopPrincipal extends MenuItem {

	public ItemMenuShopPrincipal() {
		super("§6Boutique", new String[0], new ItemStack(Material.ENDER_CHEST), true);
	}

	@Override
	public void onUse(Player player) {
		new VirtualMenuShopPrincipal(player).open(player);
	}
	
	@Override
	public void InteractOnUse(Player player) {
		new VirtualMenuShopPrincipal(player).open(player);
	}
}
