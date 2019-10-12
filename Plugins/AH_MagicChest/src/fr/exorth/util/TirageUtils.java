package fr.exorth.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;

public class TirageUtils {
	
	public static FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<ItemStack> gains = (ArrayList<ItemStack>) config.getList("gains");

	public static List<ItemStack> getList() {
		
		List<ItemStack> List = new ArrayList<>();
		
		for(ItemStack item : Utils.getValidGains()){
			int percent = Integer.parseInt(item.getItemMeta().getLore().get(0).split("%")[0]);
			
			for(int i = 0;i<percent;i++){
				List.add(item);
			}
			
		}
		
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);
		Collections.shuffle(List);	
		
		return List;
	}

	public static void roll(List<ItemStack> list, Inventory tirage, int ind) {
		
		for(int i=0;i<9;i++){
			
			tirage.setItem(i+9, list.get(ind+i));
		}
		
	}
	
	public static void end(Inventory Tirage,Player p){
		
		Tirage.setItem(4, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)5));
		Tirage.setItem(22, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)5));
		
		
		
		
		ItemStack loot = Tirage.getItem(13);
		//Bukkit.broadcastMessage(p.getUniqueId().toString() + " / " + loot.getItemMeta().getDisplayName());
		
		String BroadcastName = loot.getItemMeta().getDisplayName();
		String Name = loot.getItemMeta().getDisplayName().split("] ")[1];
		
		Bukkit.broadcastMessage("§b§l" + p.getName().toString() + " à gagné: " + BroadcastName + " §b§ldans le Coffre Magique !");
		
		String Command = config.getString("actions." + Name).replace("{joueur}", p.getName());
		
		MagicChest.getInstance().getServer().dispatchCommand(MagicChest.getInstance().getServer().getConsoleSender(), Command);
		
		MagicChest.getInstance().inTirage.remove(p.getName());
		MagicChest.getInstance().inTirageInv.remove(p.getName());
		
	}

}
