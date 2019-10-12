package fr.exorth.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class confirmation {	
	
	public void createConfirmation(ItemStack item, Player p, String type){
		
		Inventory confInv = Bukkit.createInventory(null, 27, "§cConfirmation: "+ type);
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		
		for(int i = 0; i<confInv.getSize();i++){
			int j = i%9;
			if(j==0||j==1||j==2){
				confInv.setItem(i, cancel);
			}
			else if(j==6||j==7||j==8){
				confInv.setItem(i, confirm);
			}
			else{
				confInv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
			}
		}
		confInv.setItem(13, item);
		
		p.openInventory(confInv);
		
	}
	
	public void createSellConfirmation(ItemStack item, double prix, double taxe, int hours, Player p){
		
		Inventory confInv = Bukkit.createInventory(null, 27, "§cConfirmation de mise en vente");
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		ItemStack bonachat =  createBonAchat(prix,taxe,hours);
		
		for(int i = 0; i<confInv.getSize();i++){
			int j = i%9;
			if(j==0||j==1){
				confInv.setItem(i, cancel);
			}
			else if(j==7||j==8){
				confInv.setItem(i, confirm);
			}
			else{
				confInv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));
			}
		}
		confInv.setItem(13, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
		confInv.setItem(12, item);
		confInv.setItem(14, bonachat);
		
		p.openInventory(confInv);
		
	}

	private ItemStack createBonAchat(double prix, double taxe, int hours) {
		
		ItemStack bonAchat = new ItemStack(Material.PAPER,1);
		ItemMeta bonAchatM = bonAchat.getItemMeta();
		bonAchatM.setDisplayName("§6§lBon d'Achat");
		List<String> lores = new ArrayList<>();
		lores.add("§bPrix de vente total (dreams): §a" + prix);
		lores.add("§bTemps de mise en vente (jours): §a" + hours);
		lores.add("§cVous paierez une taxe de : §4" + taxe);
		bonAchatM.setLore(lores);
		bonAchat.setItemMeta(bonAchatM);
		return bonAchat;
	}
	
}
