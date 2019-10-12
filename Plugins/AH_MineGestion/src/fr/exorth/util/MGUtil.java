package fr.exorth.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.exorth.MineGestion;
import fr.exorth.pickaxes.PickaxeType;
import fr.exorth.pickaxes.RentPickaxe;

public class MGUtil {
	
	static FileConfiguration config = MineGestion.getInstance().getConfig();

	public static boolean isVipMiner(Player p) {
		
		if(p.hasPermission("minegestion.pickaxe.stone") || p.hasPermission("minegestion.pickaxe.iron") || p.hasPermission("minegestion.pickaxe.diamond") 
				|| p.hasPermission("minegestion.pickaxe.diamond_x")){
			
			return true;
		}else{
			
			return false;
		}

	}

	public static boolean hasRent(Player p) {
		
		if(RentPickaxe.hasPickaxe(p.getUniqueId())){
			
			return true;
		}else{
			
			return false;
		}

	}

	public static ArrayList<ItemStack> getPickaxesforrent() {
		
		ArrayList<ItemStack> pickaxes = new ArrayList<>();
		
		ItemStack stone = PickaxeType.STONE.getItem();
		stone = MGItemUtil.addLore(stone, "§aA partir de "+getCost(PickaxeType.STONE)+" Dreams");
		
		ItemStack iron = PickaxeType.IRON.getItem();
		iron = MGItemUtil.addLore(iron, "§aA partir de "+getCost(PickaxeType.IRON)+" Dreams");

		ItemStack diamond = PickaxeType.DIAMOND.getItem();
		diamond = MGItemUtil.addLore(diamond, "§aA partir de "+getCost(PickaxeType.DIAMOND)+" Dreams");
		
		ItemStack diamond_x = PickaxeType.DIAMOND_X.getItem();
		diamond_x = MGItemUtil.addLore(diamond_x, "§aA partir de "+getCost(PickaxeType.DIAMOND_X)+" Dreams");
		
		
		pickaxes.add(stone);
		pickaxes.add(iron);
		pickaxes.add(diamond);
		pickaxes.add(diamond_x);
		
		return pickaxes;
	}
	
	public static ArrayList<ItemStack> getPickaxesforchange(Player p,PickaxeType type) {
		
		ArrayList<ItemStack> pickaxes = new ArrayList<>();
		
		ItemStack stone = PickaxeType.STONE.getItem();
		stone = MGItemUtil.addLore(stone, getChangeCostS(p, type,PickaxeType.STONE));
		
		ItemStack iron = PickaxeType.IRON.getItem();
		iron = MGItemUtil.addLore(iron, getChangeCostS(p, type,PickaxeType.IRON));

		ItemStack diamond = PickaxeType.DIAMOND.getItem();
		diamond = MGItemUtil.addLore(diamond, getChangeCostS(p, type,PickaxeType.DIAMOND));
		
		ItemStack diamond_x = PickaxeType.DIAMOND_X.getItem();
		diamond_x = MGItemUtil.addLore(diamond_x, getChangeCostS(p, type,PickaxeType.DIAMOND_X));
		
		
		pickaxes.add(stone);
		pickaxes.add(iron);
		pickaxes.add(diamond);
		pickaxes.add(diamond_x);
		
		return pickaxes;
		
	}

	private static String getChangeCostS(Player p, PickaxeType oldtype, PickaxeType newtype) {
		
		if(oldtype.getOrderNumber()>newtype.getOrderNumber()){
			return "§cCoût: §aGratuit";
		}
		else if(oldtype.getOrderNumber()==newtype.getOrderNumber()){
			return "§cCeci est votre offre actuelle";
		}
		else{
			
			double newPrice = getCost(newtype);
			double actualPrice = getCost(oldtype);		
			double pdif = newPrice-actualPrice;
			double pdifS = ((pdif/60)/60);
			
			long exp = RentPickaxe.getPickaxe(p.getUniqueId()).getExpiration();
			
			long expS = exp/1000;
			
			double Cost = expS*pdifS;
			
			DecimalFormat df = new DecimalFormat("#.00");
			String CostS = df.format(Cost).replace(",", ".");
			
			return "§cCoût: "+CostS+" Dreams";
			
			
		}

	}
	
	public static double getChangeCostN(Player p, PickaxeType oldtype, PickaxeType newtype) {
		
		if(oldtype.getOrderNumber()>newtype.getOrderNumber()){
			return 0;
		}
		else if(oldtype.getOrderNumber()==newtype.getOrderNumber()){
			return 0;
		}
		else{
			
			double newPrice = getCost(newtype);
			double actualPrice = getCost(oldtype);		
			double pdif = newPrice-actualPrice;
			double pdifS = ((pdif/60)/60);
			
			long exp = RentPickaxe.getPickaxe(p.getUniqueId()).getExpiration();
			
			long expS = exp/1000;
			
			double Cost = expS*pdifS;
			
			DecimalFormat df = new DecimalFormat("#.00");
			return Double.parseDouble(df.format(Cost).replace(",", "."));
			
			
		}

	}

	public static PickaxeType getType(String displayName) {

		PickaxeType type=null;
		
		switch(displayName){
		
		case "§ePioche Banale":
			type=PickaxeType.STONE;
			break;
		
		case "§bPioche Intermediare":
			type=PickaxeType.IRON;
			break;
		
		case "§cPioche Avancée":
			type=PickaxeType.DIAMOND;
			break;
		
		case "§l§dPioche Extra":
			type=PickaxeType.DIAMOND_X;
			break;
		}
		
		return type;
		
	}
	
	public static double getCost(PickaxeType type) {		
		
		double cost = config.getDouble("Prices." + type);
		
		
		return cost;
	}

	public static ItemStack getMenuRentPickaxe(Player p) {
		
		RentPickaxe rp = RentPickaxe.getPickaxe(p.getUniqueId());
		
		ItemStack pickaxe = rp.getType().getItem();
		pickaxe = MGItemUtil.addLore(pickaxe, "§cExpiration: " + getRentCoolDown(rp));
		pickaxe = MGItemUtil.addLore(pickaxe, "§c");
		pickaxe = MGItemUtil.addLore(pickaxe, "§b>Clic pour gérer la location<");
		
		
		return pickaxe;
	}

	public static String getRentCoolDown(RentPickaxe rp) {
		
		long cooldownm = rp.getExpiration();
		
		String cooldown = "§a" + String.format("%02d:%02d:%02d", 
			    TimeUnit.MILLISECONDS.toHours(cooldownm),
			    TimeUnit.MILLISECONDS.toMinutes(cooldownm) - 
			    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(cooldownm)),
			    TimeUnit.MILLISECONDS.toSeconds(cooldownm) - 
			    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(cooldownm)));
					
		return cooldown;
	}

	public static ItemStack getVipPickaxe(Player p) {
		
		if(p.hasPermission("minegestion.pickaxe.stone")){return PickaxeType.STONE.getItem();}
		else if(p.hasPermission("minegestion.pickaxe.iron")){return PickaxeType.IRON.getItem();}
		else if(p.hasPermission("minegestion.pickaxe.diamond")){return PickaxeType.DIAMOND.getItem();}
		else if(p.hasPermission("minegestion.pickaxe.diamond_x")){return PickaxeType.DIAMOND_X.getItem();}
		
		else{return null;}
	}

	public static boolean isInZone(Location loc) {
		
		Location pos1 = (Location) config.get("MineZone.pos1");
		Location pos2 = (Location) config.get("MineZone.pos2");
		
	    double[] dim = new double[2];
	    
	    dim[0] = pos1.getX();
	    dim[1] = pos2.getX();
	    Arrays.sort(dim);
	    if(loc.getX() > dim[1] || loc.getX() < dim[0])
	        return false;
	 
	    dim[0] = pos1.getZ();
	    dim[1] = pos2.getZ();
	    Arrays.sort(dim);
	    if(loc.getZ() > dim[1] || loc.getZ() < dim[0])
	        return false;
	 
	    dim[0] = pos1.getY();
	    dim[1] = pos2.getY();
	    Arrays.sort(dim);
	    if(loc.getY() > dim[1] || loc.getY() < dim[0])
	        return false;
	 
	    return true;
	}



	
	

}
