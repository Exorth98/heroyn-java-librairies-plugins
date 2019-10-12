package fr.exorth.util;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.exorth.pickaxes.PickaxeType;
import fr.exorth.pickaxes.RentPickaxe;
import fr.exorth.pickaxes.VipMiner;

public class NPCMenu {

	public static void choose(Player p) {
			
		if(MGUtil.isVipMiner(p) || MGUtil.hasRent(p)){
			
			if(MGUtil.isVipMiner(p) && !MGUtil.hasRent(p)){openPersonnalMenu(p,"vip");;}
			if(!MGUtil.isVipMiner(p) && MGUtil.hasRent(p)){openPersonnalMenu(p,"rent");;}
			if(MGUtil.isVipMiner(p) && MGUtil.hasRent(p)){openPersonnalMenu(p,"both");;}
			
			}
		else{openRentMenu(p);}
		
	}

	public static void openRentMenu(Player p) {
		
		Inventory rentMenu = Bukkit.createInventory(null, 45, "§0Location de pioches");
		
		for(int i=0;i<9;i++){rentMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		for(int i=9;i<36;i++){rentMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		for(int i=36;i<45;i++){rentMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		infos = MGItemUtil.setName(infos, "§6Informations");
		infos = MGItemUtil.setLores(infos, Arrays.asList(
				"§c",
				"§cTu n'as aucune location active",
				"",
				"§aClic sur une pioche pour la louer"));
		rentMenu.setItem(26, infos);
		
		ArrayList<ItemStack> pickaxes = MGUtil.getPickaxesforrent();
		int j=0;
		
		for(int i=18;i<25;i+=2,j++){rentMenu.setItem(i, pickaxes.get(j));}
		
		
		if(MGUtil.isVipMiner(p)){
			
			ItemStack back = new ItemStack(Material.ARROW,1);
			back = MGItemUtil.setName(back, "§cRetour");
			
			rentMenu.setItem(36, back);
			
		}

		
		p.openInventory(rentMenu);
		
	}
	
	public static void openRentChoseTimeMenu(Player p, PickaxeType type) {
		
		Inventory rentchoseMenu = Bukkit.createInventory(null, 45, "§0Choix du temps de location");

		
		for(int i=0;i<9;i++){rentchoseMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		for(int i=9;i<36;i++){rentchoseMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		for(int i=36;i<45;i++){rentchoseMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		
		
		ItemStack pickaxe = type.getItem();
		rentchoseMenu.setItem(4, pickaxe);
		
		double Cost = MGUtil.getCost(type);
		
		for(int i=1,j=19;i<=3;i++,j+=2){
			
			ItemStack hour = new ItemStack(Material.PAPER,i);
			hour = MGItemUtil.setName(hour, "§6"+i+" Heure(s)");
			hour = MGItemUtil.setLores(hour, Arrays.asList(
					"§c",
					"§a>Clic pour choisir<",
					"§ePrix: §b" + Cost*i));
			rentchoseMenu.setItem(j, hour);
			
		}
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		infos = MGItemUtil.setName(infos, "§6Informations");
		infos = MGItemUtil.setLores(infos, Arrays.asList(
				"§c",
				"§bCette durée est relevée sur le temps",
				"§bpassé §cdans la mine",
				"§c",
				"§aIl est possible d'allonger la durée",
				"§ade location a tout moment"));
		rentchoseMenu.setItem(25, infos);
		
		ItemStack back = new ItemStack(Material.ARROW,1);
		back = MGItemUtil.setName(back, "§cRetour");
		
		rentchoseMenu.setItem(36, back);
		
		
		p.openInventory(rentchoseMenu);
		
	}
	
	public static void openRentAddTimeMenu(Player p, PickaxeType type) {
		
		Inventory rentchoseMenu = Bukkit.createInventory(null, 45, "§0Ajout de temps de location");
		
		ItemStack pickaxe = type.getItem();
		rentchoseMenu.setItem(4, pickaxe);
		
		for(int i=0;i<9;i++){rentchoseMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		for(int i=9;i<36;i++){rentchoseMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		for(int i=36;i<45;i++){rentchoseMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		
		double Cost = MGUtil.getCost(type);
		
		for(int i=1,j=19;i<=3;i++,j+=2){
			
			ItemStack hour = new ItemStack(Material.PAPER,i);
			hour = MGItemUtil.setName(hour, "§6+ "+i+" Heure(s)");
			hour = MGItemUtil.setLores(hour, Arrays.asList(
					"§c",
					"§a>Clic pour choisir<",
					"§ePrix: §b" + Cost*i));
			rentchoseMenu.setItem(j, hour);
			
		}
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		infos = MGItemUtil.setName(infos, "§6Informations");
		infos = MGItemUtil.setLores(infos, Arrays.asList(
				"§c",
				"§bCette durée est relevée sur le temps",
				"§bpassé §cdans la mine"));
		rentchoseMenu.setItem(25, infos);
		
		ItemStack back = new ItemStack(Material.ARROW,1);
		back = MGItemUtil.setName(back, "§cRetour");
		
		rentchoseMenu.setItem(36, back);
		
		
		p.openInventory(rentchoseMenu);
		
	}

	public static void openPersonnalMenu(Player p, String mode) {
		
		Inventory PersoMenu = Bukkit.createInventory(null, 45, "§0Gestion de mes pioches");
		
		for(int i=0; i<PersoMenu.getSize()-1;i++){			
			if(i%9==1 || i%9==3 || i%9==5 || i%9==7){PersoMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
			else{PersoMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}			
		}
		for(int i=0;i<9;i++){PersoMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		for(int i=36;i<45;i++){PersoMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		
		PersoMenu.setItem(29, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
		PersoMenu.setItem(33, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
		
		if(mode.equals("vip") || mode.equals("rent")){
			
			PersoMenu.setItem(11, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
			PersoMenu.setItem(15, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));
			
			if(mode.equals("vip")){
				
				ItemStack emerald = new ItemStack(Material.EMERALD,1);
				emerald = MGItemUtil.setName(emerald, "§6Aucune pioche en location");
				emerald = MGItemUtil.addLore(emerald, "§b>Loues une pioche ici<");
				PersoMenu.setItem(20, emerald);	
				
				ItemStack pickaxe = MGUtil.getVipPickaxe(p);
				pickaxe = MGItemUtil.addLore(pickaxe, "§bPioche comprise dans ton grade VIP");
				PersoMenu.setItem(24, pickaxe);
				
				
			}else if(mode.equals("rent")){
				
				ItemStack barrier = new ItemStack(Material.BARRIER,1);
				barrier = MGItemUtil.setName(barrier, "§6§lPioche Vip");
				barrier = MGItemUtil.addLore(barrier, "§cAucune pioche incluse dans votre grade");
				PersoMenu.setItem(24, barrier);				
				
				ItemStack pickaxe = MGUtil.getMenuRentPickaxe(p);
				PersoMenu.setItem(20, pickaxe);
			}
		}else{ // both
			
			VipMiner vm = new VipMiner(p);
			String pref = vm.getPref();
			
			
			@SuppressWarnings("deprecation")
			ItemStack none = new ItemStack(351,1,(byte)8);
			none = MGItemUtil.setName(none, "§7[Pioche non séléctionnée]");
			none = MGItemUtil.addLore(none, "§aClic pour séléctionner cette pioche");
			
			@SuppressWarnings("deprecation")
			ItemStack selected = new ItemStack(351,1,(byte)10);
			selected = MGItemUtil.setName(selected, "§a[Pioche séléctionnée]");
			
			if(pref.equals("rent")){
				
				PersoMenu.setItem(11, selected);
				PersoMenu.setItem(15, none);
				
			}else{ //vip
				
				PersoMenu.setItem(15, selected);
				PersoMenu.setItem(11, none);
				
			}
			
			ItemStack pickaxevip = MGUtil.getVipPickaxe(p);
			pickaxevip = MGItemUtil.addLore(pickaxevip, "§bPioche comprise dans ton grade VIP");
			PersoMenu.setItem(24, pickaxevip);
			
			ItemStack pickaxerent = MGUtil.getMenuRentPickaxe(p);
			PersoMenu.setItem(20, pickaxerent);
		}
		
		p.openInventory(PersoMenu);
	}

	public static void openRentOptionsMenu(Player p){

		
		Inventory RentOptionsMenu = Bukkit.createInventory(null, 27, "§0Options de location");
		
		for(int i=0; i<RentOptionsMenu.getSize();i++){			
			if(i%9==0 || i%9==4 || i%9==8){RentOptionsMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
			else{RentOptionsMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}			
		}
		
		ItemStack pickaxe = RentPickaxe.getPickaxe(p.getUniqueId()).getType().getItem();
		pickaxe = MGItemUtil.addLore(pickaxe, "§b> Changer de pioche <");
		RentOptionsMenu.setItem(11, pickaxe);
		
		ItemStack time = new ItemStack(Material.PAPER,1);
		time = MGItemUtil.setName(time, "§6Temps de location");
		time = MGItemUtil.addLore(time, "§cExpiration: §a" + MGUtil.getRentCoolDown(RentPickaxe.getPickaxe(p.getUniqueId())));
		time = MGItemUtil.addLore(time, "§b> Prolonger la durée de location <");
		RentOptionsMenu.setItem(15, time);
		
		ItemStack back = new ItemStack(Material.ARROW,1);
		back = MGItemUtil.setName(back, "§cRetour");
		
		RentOptionsMenu.setItem(18, back);
		
		p.openInventory(RentOptionsMenu);
		
	}
	
	public static void openChangePickaxeMenu(Player p){
		
		Inventory rentMenu = Bukkit.createInventory(null, 45, "§0Changement de pioche");
		
		for(int i=0;i<9;i++){rentMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		for(int i=9;i<36;i++){rentMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1));}
		for(int i=36;i<45;i++){rentMenu.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE,1,(byte)7));}
		
		ItemStack infos = new ItemStack(Material.BOOK,1);
		infos = MGItemUtil.setName(infos, "§6Informations");
		infos = MGItemUtil.setLores(infos, Arrays.asList(
				"§c",
				"§cLe prix varie selon la pioche",
				"§cet le temps de location actuels"));
		rentMenu.setItem(26, infos);
		
		ArrayList<ItemStack> pickaxes = MGUtil.getPickaxesforchange(p, RentPickaxe.getPickaxe(p.getUniqueId()).getType());
		int j=0;
		
		for(int i=18;i<25;i+=2,j++){rentMenu.setItem(i, pickaxes.get(j));}
		
		ItemStack back = new ItemStack(Material.ARROW,1);
		back = MGItemUtil.setName(back, "§cRetour");
		
		rentMenu.setItem(36, back);
		
		p.openInventory(rentMenu);
		
	}

}
