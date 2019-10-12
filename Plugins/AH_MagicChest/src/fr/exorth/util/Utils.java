package fr.exorth.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MagicChest;

public class Utils {
	
	public static FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> keys = (ArrayList<String>) config.getList("keys");
	@SuppressWarnings("unchecked")
	static ArrayList<String> bkeys = (ArrayList<String>) config.getList("boughtkeys");
	@SuppressWarnings("unchecked")
	static ArrayList<ItemStack> gains = (ArrayList<ItemStack>) config.getList("gains");

	
	public static BlockFace getDir(Player p){
		
	    float yaw = p.getLocation().getYaw();
	    if (yaw < 0) {
	        yaw += 360;
	    }
	    if (yaw >= 315 || yaw < 45) {
	        return BlockFace.SOUTH;
	    } else if (yaw < 135) {
	        return BlockFace.WEST;
	    } else if (yaw < 225) {
	        return BlockFace.NORTH;
	    } else if (yaw < 315) {
	        return BlockFace.EAST;
	    }
	    return BlockFace.NORTH;
	
	}
	
	public static int countKeys(Player p){
		
		Inventory inv = p.getInventory();
		
		int count = 0;
		
		for(int i =0; i<inv.getSize(); i++){
			ItemStack item = inv.getItem(i);
			
			if(item != null){
			
				if(item.getType() == Material.TRIPWIRE_HOOK && item.getItemMeta().getDisplayName().equals("§a§lClé Magique")){
					String ref = ItemUtil.getRef(item);
					if(keys == null){keys = new ArrayList<String>();}
					
					if(keys.contains(ref)){
						
						count += item.getAmount();
						
					}				
				}
				
			}
		}
		
		return count;
	}

	public static boolean checkForKey(Player p) {
		
		Inventory inv = p.getInventory();
		
		for(int i =0; i<inv.getSize(); i++){
			ItemStack item = inv.getItem(i);
			
			if(item != null){
				
				if(item.getType() == Material.TRIPWIRE_HOOK && item.getItemMeta().getDisplayName().equals("§a§lClé Magique")){
					String ref = ItemUtil.getRef(item);
					if(keys == null){keys = new ArrayList<String>();}
					
					if(keys.contains(ref)){
						
						keys.remove(ref);
						config.set("keys", keys);
						MagicChest.getInstance().saveConfig();
						
						if(item.getAmount()==1){p.getInventory().remove(item);}
						else{item.setAmount(item.getAmount()-1);}
						
						return true;
					
					}else{
						p.sendMessage("§cCette clé n'est pas valide ! Suppression ...");
						p.getInventory().remove(item);
					}
					
				}
			}

			
		}
		p.sendMessage("§cTu n'as pas de clé à utiliser");
		
		return false;
	}

	public static ItemStack getMagicKey() {
		
		ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK);
		
		Random r = new Random();
		String ref = Integer.toString(1000000 + r.nextInt(9999999 - 1000000));		
		
		key = ItemUtil.setName(key,"§a§lClé Magique");
		key = ItemUtil.setLores(key,Arrays.asList("§c  ","§6Utilisable pour ouvrir un coffre magique","§cNuméro de validité: " + ref));
		
		return key;
		
	}

	public static String getPref(String s) {
		
		String pref;
		
		switch (s){
		
		case "Commun":
			pref = "§e[Commun]";
			break;
		case "Rare":
			pref = "§b[Rare]";
			break;
		case "Epique":
			pref = "§d[Epique]";
			break;
		case "Légendaire":
			pref = "§a§l[Légendaire]";
			break;
		default:
			pref = "§e[Commun]";
		
		}
		
		return pref;

	}
	
	public static List<String> getGainsName(){
		ArrayList<String> names = new ArrayList<>();
		
		if(gains == null){gains = new ArrayList<ItemStack>();}
		
		for (ItemStack item : gains){
			String name = item.getItemMeta().getDisplayName().split("] ")[1];
			names.add(name);
		}
		
		return names;
		
	}
	
	public static List<ItemStack> getValidGains(){
		ArrayList<ItemStack> gainsV = new ArrayList<>();
		
		if(gains == null){gains = new ArrayList<ItemStack>();}
		
		for (ItemStack item : gains){
			String name = item.getItemMeta().getDisplayName().split("] ")[1];
			String Command = config.getString("actions." + name);
			if(!Command.contains("[X]")){
				gainsV.add(item);
			}
		}
		
		return gainsV;
		
	}
	
	public static void addBoughtKeys(String uuid, int amount){
		
		if(bkeys == null){bkeys = new ArrayList<String>();}
		boolean b = false;
		
		for(String line : bkeys ){
			
			if(line.contains(uuid)){
				int actualAmount = Integer.parseInt(line.split(":")[1]);
				int newAmount = actualAmount + amount;
				
				int i = bkeys.indexOf(line);
				
				bkeys.set(i, uuid + ":" + newAmount);
				

				b = true;
			}			
		}
		
		if(!b){		
			bkeys.add(uuid + ":" + amount);
		}
		config.set("boughtkeys", bkeys);
		MagicChest.getInstance().saveConfig();
	}
	
	public static void removeOneBoughtKeys(String uuid){
		
		if(bkeys == null){bkeys = new ArrayList<String>();}
		
		for(String line : bkeys ){
			
			if(line.contains(uuid)){
				int actualAmount = Integer.parseInt(line.split(":")[1]);
				int newAmount = actualAmount - 1;
				
				int i = bkeys.indexOf(line);
				
				bkeys.set(i, uuid + ":" + newAmount);
				
			}			
		}
		config.set("boughtkeys", bkeys);
		MagicChest.getInstance().saveConfig();
	}
	
	public static int getBoughtKeyNumber(String uuid){
		
		if(bkeys == null){bkeys = new ArrayList<String>();}

		int amount = 0;
		
		for(String line : bkeys ){
			
			if(line.contains(uuid)){
				
				amount = Integer.parseInt(line.split(":")[1]);
			}			
		}
		
		return amount;
	}
	
	
}
