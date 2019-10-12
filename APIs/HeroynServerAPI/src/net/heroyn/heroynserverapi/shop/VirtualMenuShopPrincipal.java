package net.heroyn.heroynserverapi.shop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.heroyn.heroynapi.virtualmenu.VirtualMenu;
import net.heroyn.heroynserverapi.shop.Minion.ItemMenuVirtualShopMinions;
import net.heroyn.heroynserverapi.shop.particle.ItemMenuVirtualShopParticle;

public class VirtualMenuShopPrincipal extends VirtualMenu {

	Player player;
	
	public VirtualMenuShopPrincipal(Player player) {
		super("§aBoutique", 6);
		this.player = player;
	}

	@Override
	public Inventory getInventory() {
		if (this.inventory == null) {
			this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
			setMenuItem(getPos(1, 1), new ItemMenuVirtualShopMinions());
			setMenuItem(getPos(1, 2), new ItemMenuVirtualShopParticle());
		}
		return this.inventory;
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub
		
	}
	
	
}
