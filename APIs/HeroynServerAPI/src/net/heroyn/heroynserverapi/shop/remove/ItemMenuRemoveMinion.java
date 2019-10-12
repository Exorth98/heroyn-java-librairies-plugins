package net.heroyn.heroynserverapi.shop.remove;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.utils.MsgUtils;

public class ItemMenuRemoveMinion extends MenuItem {

	public ItemMenuRemoveMinion() {
		super("§cSupprimer", new String[] { "§6Supprime votre Familliers" }, new ItemStack(Material.BARRIER));
	}

	@Override
	public void onUse(Player player) {
		PlayerInfo pp = PlayerInfo.instanceOf(player);
		if (pp.getMinion() != null) {
			pp.getMinion().removeMinion();
			pp.setMinion(null);
			player.sendMessage(MsgUtils.BOUTIQUE_PREFIX + " §cVous avez supprimé votre Familliers !");
			player.closeInventory();
		} else {
			player.sendMessage(MsgUtils.BOUTIQUE_PREFIX + " §cVous n'avez pas de familliers actif !");
			player.closeInventory();
		}
	}
}
