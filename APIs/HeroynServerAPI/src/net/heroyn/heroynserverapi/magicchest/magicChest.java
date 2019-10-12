package net.heroyn.heroynserverapi.magicchest;

import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.heroyn.heroynapi.utils.MathUtils;
import net.heroyn.heroynapi.utils.Task.TaskManager;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.threedimensions.Item3D;
import net.heroyn.heroynserverapi.utils.MsgUtils;

public class magicChest {

	public magicChestEnum chestEnum;
	public PlayerInfo playerInfo;

	public magicChest(Player player, magicChestEnum chestEnum) {
		this.playerInfo = PlayerInfo.instanceOf(player);
		this.chestEnum = chestEnum;
	}
	

	/**
	 * Ouvre le coffre
	 */
	public void open() {
		virtualEffect();
	}

	/**
	 * Ouvre le coffre commun
	 */
	private void openChest(RarityEnum... rarity) {
		List<CosmeticsEnum> list = CosmeticsEnum.getCosmeticsWithRarity(rarity);
		int nbrCosmetics = MathUtils.random(1, 2);
		if (nbrCosmetics == 1) {
			addCosmeticsToPlayer(list);
		} else if (nbrCosmetics == 2) {
			addCosmeticsToPlayer(list);
			addCosmeticsToPlayer(list);
		}
	}

	/**
	 * Ajoute un cosmetics aux joueur
	 * 
	 * @param list
	 */
	private void addCosmeticsToPlayer(List<CosmeticsEnum> list) {
		Random rand = new Random();
		int random = rand.nextInt(list.size());
		CosmeticsEnum cosmetics = list.get(random);

		if (playerHasCosmetics(cosmetics)) {
			getPlayerInfo().getPlayer().sendMessage("§7Vous gagné de la poussière d'ange ! x10");
			// AJOUT DE LA POUSSIERE D'ANGE
			return;
		} else {
			getPlayerInfo().addPermissions(cosmetics.getPermissions());
			MsgUtils.achatCosmetics(getPlayer(), cosmetics);
		}
	}

	private void virtualEffect() {
		for (int x = 0; x <= 5; x++) {
			ItemStack it = new ItemStack(Material.ENDER_CHEST);
			ItemMeta im = it.getItemMeta();
			im.setDisplayName("&5" + x);
			it.setItemMeta(im);
			Item3D item = new Item3D(getPlayer(), it);
			item.setPosition(getPlayer().getLocation().clone().add(1, 0, 0), 1.0f);
			item.getSelector().setVisible(true);
		}
		TaskManager.runTaskLater(new Runnable() {
			@Override
			public void run() {
				openChest(getChestEnum().getRarity());
			}
		}, 20 * 2);
	}

	/**
	 * Return si le joueur as deja le cosmetics
	 * 
	 * @param cosmetics
	 * @return
	 */
	private boolean playerHasCosmetics(CosmeticsEnum cosmetics) {
		if (getPlayerInfo().hasPermissions(cosmetics.getPermissions())) {
			return true;
		}
		return false;
	}

	private magicChestEnum getChestEnum() {
		return chestEnum;
	}

	private PlayerInfo getPlayerInfo() {
		return playerInfo;
	}

	private Player getPlayer() {
		return getPlayerInfo().getPlayer();
	}
}
