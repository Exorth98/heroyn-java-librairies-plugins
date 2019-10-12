package fr.exorth.util;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import fr.exorth.MelonSale;

public class MelonSaleUtils {
	
	static FileConfiguration config = MelonSale.getInstance().getConfig();
	

	public static void CreateConfirmation(Player p, String Type){
		
		Inventory confInv = Bukkit.createInventory(null, 27, "§0Confirmation de vente");
		ItemStack confirm = new ItemStack(Material.STAINED_CLAY,1,(byte)5);
		ItemMeta confirmM = confirm.getItemMeta();confirmM.setDisplayName("§a§lConfirmer");confirm.setItemMeta(confirmM);				
		ItemStack cancel = new ItemStack(Material.STAINED_CLAY,1,(byte)14);
		ItemMeta cancelM = cancel.getItemMeta();cancelM.setDisplayName("§c§lAnnuler");cancel.setItemMeta(cancelM);
		ItemStack bonvente =  createBonVente(p,Type);
		ItemStack item = getSaleItem(Type);
		
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
		confInv.setItem(14, bonvente);
		
		int amount = getAmount(Type,p);
		double price = getPrice(Type, amount);
		
		double[] infos = {amount, price};
		MelonSale.getInstance().inConfirmation.put(p, infos);
		
		p.openInventory(confInv);
		
	}

	private static ItemStack createBonVente(Player p, String Type) {
		
		ItemStack bonVente = new ItemStack(Material.PAPER,1);
		ItemMeta bonVenteM = bonVente.getItemMeta();
		bonVenteM.setDisplayName("§6§lBon de vente");
		List<String> lores = new ArrayList<>();
		
		int amount = getAmount(Type,p);
		double price = getPrice(Type, amount);
		
		String sType =getSType(getSaleItem(Type));
		
		lores.add("§bVous allez vendre: §a"+amount+" " + sType);
		lores.add("§bPour un total de §a"+price+" Dreams");
		
		bonVenteM.setLore(lores);
		bonVente.setItemMeta(bonVenteM);
		return bonVente;
	}

	private static double getPrice(String type, int amount) {
		
		ItemStack item = getSaleItem(type);		
		double MelonPrice = (double) config.get("SellPrice.melon");
		double CactusPrice = (double) config.get("SellPrice.melon");
		
		if(item.getType()==Material.MELON_BLOCK){return (amount*MelonPrice);}
		if(item.getType()==Material.CACTUS){return (amount*CactusPrice);}
		
		else{
			return 0;
		}
	}


	private static int getAmount(String type, Player p) {
		
		ItemStack item = getSaleItem(type);
		Inventory pInv = p.getInventory();
		
		int total=0;
		
		ItemStack[] items = pInv.getContents();
        for (ItemStack i : items)
        {
            if ((i != null) && (i.getType()==item.getType()) && (i.getAmount() > 0))
            {
                total += i.getAmount();
            }
        }
        return total;

	}
	

	private static ItemStack getSaleItem(String type) {
		
		if(type.equalsIgnoreCase("melon")){
			
			return new ItemStack(Material.MELON_BLOCK,1);
			
		}else{
			return new ItemStack(Material.CACTUS,1);
		}
		
	}
	
    private static String getSType(ItemStack item) {
		
    	switch(item.getType()){
    	
    	case MELON_BLOCK:
    		return "Melons";
    	case CACTUS:
    		return "Cactus";
		default:
			return "???";
    	
    	}

	}

}
