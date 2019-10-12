package fr.exorth.util;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChestMenu {

	public static void open(Player p) {
		
		Inventory chest = Bukkit.createInventory(null, 27, "�l�0Coffre Magique");

		for(int i=0;i<chest.getSize() ;i++){
			chest.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
		}

		
		ItemStack tirage = new ItemStack(Material.ENDER_CHEST,1);
		ItemMeta tirageM =  tirage.getItemMeta();
		tirageM.setDisplayName("�a�lEffectuer un tirage");
		tirageM.setLore(Arrays.asList("�a  ","�6Mes cl�s valides: �3�l" + Utils.countKeys(p)));
		tirage.setItemMeta(tirageM);
		chest.setItem(13, tirage);
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		ItemMeta infosM = infos.getItemMeta();
		infosM.setDisplayName("�6Informations");
		infosM.setLore(Arrays.asList(
				"�1  ",
				"�3Obtenez une cl� en votant, lors d'events,",
				"�3ou sur la boutique",
				"�6-----------------------------------------------",
				"�3Pour effectuer un tirage :",
				"�3Cliquer sur l'enderchest",
				"�6-----------------------------------------------",
				"�cIl vous faut une cl� valide dans votre inventaire !",
				"�cou des cl�s achet�es !"
				));
		
		infos.setItemMeta(infosM);
		chest.setItem(18,infos );
		
		ItemStack gains = new ItemStack(Material.PAPER,1);
		gains = ItemUtil.setName(gains, "�aMes gains en attente");
		gains = ItemUtil.setLores(gains, Arrays.asList("�c ",
				                                       "�eVous avez�b " + GainUtils.getGainsNumber(p) + " �egains en attente",
				                                       "�a>Cliques pour les r�cup�rer<"));
		chest.setItem(15, gains);
		
		ItemStack bkeys = new ItemStack(Material.TRIPWIRE_HOOK,1);
		bkeys = ItemUtil.setName(bkeys, "�aRetirer une cl�");
		bkeys = ItemUtil.setLores(bkeys, Arrays.asList("�c ",
													   "�eVous avez�b " + Utils.getBoughtKeyNumber(p.getUniqueId().toString()) + " �ecl�s stock�es ",
													   "�a> Cliques pour retirer 1 cl� <"));
		chest.setItem(11, bkeys);
		
		p.openInventory(chest);
		
	}

}
