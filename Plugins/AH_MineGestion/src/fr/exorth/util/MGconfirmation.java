package fr.exorth.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.MineGestion;
import fr.exorth.pickaxes.PickaxeType;
import fr.exorth.pickaxes.RentPickaxe;

public class MGconfirmation {

	public static void CreateRentConfirmation(Player p, int hours, PickaxeType pt) {
		
		Inventory confInv = Bukkit.createInventory(null, 27, "§0Confirmation de Location");
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		
		double price = MGUtil.getCost(pt)*hours;
		
		ItemStack bonloc =  createBonLoc(pt,hours,price);
		ItemStack pickaxe = pt.getItem();
		
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
		confInv.setItem(12, pickaxe);
		confInv.setItem(14, bonloc);
		
		double[] infos = {hours,price};
		MineGestion.getInstance().inRentConfirmation.put(p, infos);
		
		p.openInventory(confInv);
		
		
	}

	private static ItemStack createBonLoc(PickaxeType pt, int hours,double price) {
		
		ItemStack bonLoc = new ItemStack(Material.PAPER,1);
		ItemMeta bonLocM = bonLoc.getItemMeta();
		bonLocM.setDisplayName("§6§lBon de location");
		List<String> lores = new ArrayList<>();
		
		lores.add("§bDurée choisie: §a"+hours+" heures");
		lores.add("§bPrix total: §a"+price+" Dreams");
		
		bonLocM.setLore(lores);
		bonLoc.setItemMeta(bonLocM);
		return bonLoc;

	}
	
	private static ItemStack createBonAddTime(PickaxeType pt, int hours,double price) {
		
		ItemStack bonLoc = new ItemStack(Material.PAPER,1);
		ItemMeta bonLocM = bonLoc.getItemMeta();
		bonLocM.setDisplayName("§6§lBon de prolongement de location");
		List<String> lores = new ArrayList<>();
		
		lores.add("§bDurée de prolongement: §a"+hours+" heures");
		lores.add("§bPrix total: §a"+price+" Dreams");
		
		bonLocM.setLore(lores);
		bonLoc.setItemMeta(bonLocM);
		return bonLoc;

	}

	public static void CreateTimeAddConfirmation(Player p, int hours) {
		
		Inventory confInv = Bukkit.createInventory(null, 27, "§0Confirmation de prolongement de Location");
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		
		RentPickaxe rp = RentPickaxe.getPickaxe(p.getUniqueId());
		PickaxeType pt = rp.getType();
		
		double price = MGUtil.getCost(pt)*hours;
		
		ItemStack bonloc =  createBonAddTime(pt,hours,price);
		ItemStack pickaxe = pt.getItem();
		
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
		confInv.setItem(12, pickaxe);
		confInv.setItem(14, bonloc);
		
		double[] infos = {hours,price};
		MineGestion.getInstance().inTimeAddConfirmation.put(p, infos);
		
		p.openInventory(confInv);
		
		
	}

	public static void CreatePickaxeChangeConfirmation(Player p, PickaxeType ptwanted) {
		
		Inventory confInv = Bukkit.createInventory(null, 27, "§0Confirmation de changement de pioche");
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		
		RentPickaxe rp = RentPickaxe.getPickaxe(p.getUniqueId());
		PickaxeType pt = rp.getType();
		
		double price = MGUtil.getChangeCostN(p, pt, ptwanted);
		
		ItemStack bonloc =  createBonChangePickaxe(ptwanted,price);
		ItemStack pickaxe = ptwanted.getItem();
		
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
		confInv.setItem(12, pickaxe);
		confInv.setItem(14, bonloc);
		
		double infos = price;
		MineGestion.getInstance().inPickaxeChangeConfirmation.put(p, infos);
		
		p.openInventory(confInv);
		
	}

	private static ItemStack createBonChangePickaxe(PickaxeType ptwanted, double price) {
		
		ItemStack bonLoc = new ItemStack(Material.PAPER,1);
		ItemMeta bonLocM = bonLoc.getItemMeta();
		bonLocM.setDisplayName("§6§lBon de changement de pioche");
		List<String> lores = new ArrayList<>();
		
		lores.add("§bPrix total: §a"+price+" Dreams");
		
		bonLocM.setLore(lores);
		bonLoc.setItemMeta(bonLocM);
		return bonLoc;
		
		
	}

}
