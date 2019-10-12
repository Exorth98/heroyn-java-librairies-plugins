package net.heroyn.heroynserverapi.shop.virtualshop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.heroyn.heroynserverapi.shop.particle.ShopParticleApocalyptic;
import net.heroyn.heroynserverapi.shop.particle.ShopParticleAureole;
import net.heroyn.heroynserverapi.shop.particle.ShopParticleWings;
import net.heroyn.heroynserverapi.shop.remove.ItemMenuRemoveParticle;
import net.heroyn.heroynserverapi.utils.VirtualShop;

public class VirtualMenuShopParticle extends VirtualShop {

	Player player;
	
	public VirtualMenuShopParticle(Player player) {
		super("§aAnimations", 6);
		this.player = player;
	}

	@Override
	public Inventory getInventory() {
		if (this.inventory == null) {
			this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
			setItem(getPos(1, 5), new ItemMenuRemoveParticle().getItems());
			setShopItem(getPos(2, 1), new ShopParticleAureole(player));
			setShopItem(getPos(2, 2), new ShopParticleWings(player));
			setShopItem(getPos(2, 3), new ShopParticleApocalyptic(player));
		}
		return this.inventory;
	}

	@Override
	public void open(Player player) {
		player.openInventory(getInventory());
	}
}
