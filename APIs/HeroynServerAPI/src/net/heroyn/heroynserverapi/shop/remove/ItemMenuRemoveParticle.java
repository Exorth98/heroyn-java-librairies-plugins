package net.heroyn.heroynserverapi.shop.remove;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.utils.MsgUtils;

public class ItemMenuRemoveParticle extends MenuItem {

	public ItemMenuRemoveParticle() {
		super("§cSupprimer", new String[] { "§6Supprimer vos Animations" }, new ItemStack(Material.BARRIER));
	}

	@Override
	public void onUse(Player player) {
		PlayerInfo pp = PlayerInfo.instanceOf(player);
		if (pp.getParticle() != null) {
			pp.getParticle().stopPlayingParticle();
			pp.setParticle(null);
			player.sendMessage(MsgUtils.BOUTIQUE_PREFIX + " §cVous avez supprimé votre animations !");
			player.closeInventory();
		} else {
			player.sendMessage(MsgUtils.BOUTIQUE_PREFIX + " §cVous n'avez pas d'animations actif !");
			player.closeInventory();
		}
	}
}
