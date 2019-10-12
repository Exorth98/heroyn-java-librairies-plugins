package net.heroyn.heroynserverapi.shop.virtualshop;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import net.heroyn.heroynserverapi.shop.Minion.ShopMinionAstronaut;
import net.heroyn.heroynserverapi.shop.Minion.ShopMinionGhost;
import net.heroyn.heroynserverapi.shop.Minion.ShopMinionMiner;
import net.heroyn.heroynserverapi.shop.Minion.ShopMinionWitch;
import net.heroyn.heroynserverapi.shop.remove.ItemMenuRemoveMinion;
import net.heroyn.heroynserverapi.utils.VirtualShop;

public class VirtualMenuShopMinions extends VirtualShop {

	Player player;
	
	public VirtualMenuShopMinions(Player player) {
		super("§aFamilliers", 6);
		this.player = player;
	}

	@Override
	public Inventory getInventory() {
		if (this.inventory == null) {
			this.inventory = Bukkit.createInventory(null, getSize(), getTitle());
			setItem(getPos(1, 5), new ItemMenuRemoveMinion().getItems());
			setShopItem(getPos(2, 1), new ShopMinionWitch(player));
			setShopItem(getPos(2, 2), new ShopMinionMiner(player));
			setShopItem(getPos(2, 3), new ShopMinionGhost(player));
			setShopItem(getPos(2, 4), new ShopMinionAstronaut(player));
		}
		return this.inventory;
	}

	@Override
	public void open(Player player) {
		player.openInventory(getInventory());
	}
}
