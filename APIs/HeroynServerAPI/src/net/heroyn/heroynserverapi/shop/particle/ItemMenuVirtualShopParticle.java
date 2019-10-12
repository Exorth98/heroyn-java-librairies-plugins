package net.heroyn.heroynserverapi.shop.particle;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynserverapi.shop.virtualshop.VirtualMenuShopParticle;

public class ItemMenuVirtualShopParticle extends MenuItem {

	public ItemMenuVirtualShopParticle() {
		super("§6Particles", new String[] {"§6Ayez la classe"}, new ItemStack(Material.GLOWSTONE_DUST));
	}
	
	@Override
	public void onUse(Player player) {
		new VirtualMenuShopParticle(player).open(player);
	}

}
