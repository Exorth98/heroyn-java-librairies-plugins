package net.heroyn.mobarena.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.MobarenaGame;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.game.player.MobarenaSpectator;
import net.heroyn.mobarena.game.player.MobarenaSuitingupPlayer;

public class HeroynMobarenaUtils {


	public static HashMap<Player,Arena> configuratingButton = new HashMap<>();
	public static int ButtonTask;
	
	@SuppressWarnings("deprecation")
	public static void configureButton(Player p, Arena ar) {
		
		if(!configuratingButton.containsKey(p)) {
			
			configuratingButton.put(p, ar);
			
			ButtonTask = Bukkit.getScheduler().scheduleAsyncDelayedTask(HeroynMobarena.getInstance(), new Runnable() {

				@Override
				public void run() {
					
					p.sendMessage("§cTemps pour la configuration du bouton écoulé");
					configuratingButton.remove(p, ar);
				}
				
				
			}, 100L);
			
			
		}else {
			
			p.sendMessage("Tu es déja en configuration de bouton");
		}
		
	}
	
	public static void configureButtonSuccess(Player p, Arena ar, Location loc) {
		
		configuratingButton.remove(p, ar);
		
		Bukkit.getScheduler().cancelTask(ButtonTask);
		
		p.sendMessage("§eBouton ajouté avec succès");
		
		ar.addBonusButton(loc);
		
	}
	
	public static boolean classExists(String clas) {
		
		@SuppressWarnings("unchecked")
		ArrayList<String> classes = (ArrayList<String>) HeroynMobarena.getInstance().getConfig().get("Classes");
		if(classes == null){classes = new ArrayList<String>();}
		
		return classes.contains(clas);
	}

	public static boolean isInFight(Player p) {
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(MobarenaFighter maF : game.getFighters()) {
				
				if(maF.getPlayer().equals(p)) return true;
				
			}
			
		}
		
		return false;
	}
	
	public static boolean isInGameSpectate(Player p) {
		
		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(MobarenaSpectator maS : game.getSpectators()) {
				
				if(maS.getPlayer().equals(p)) return true;
				
			}
			
		}
		
		return false;
	}

	public static boolean isSuitingup(Player p) {

		for(MobarenaGame game : HeroynMobarena.getInstance().games.values()) {
			
			for(MobarenaSuitingupPlayer maSupP : game.getSuitingupPlayers()) {
				
				if(maSupP.getPlayer().equals(p)) return true;
				
			}
			
		}
		
		return false;
	}

	public static boolean isInGame(Player p) {
		
		return isInFight(p) || isInGameSpectate(p) || isSuitingup(p);
	}

	public static List<ItemStack> getKitList(ItemStack[] contents, ItemStack weapon, ItemStack sWeapon, ItemStack[] armor) {
		
		List<ItemStack> kit = new ArrayList<>();
		
		for(ItemStack item : contents)		
			if(item !=null)
				if(!item.isSimilar(weapon) && !item.isSimilar(sWeapon) && !containsSimilar(armor,item))
					kit.add(item);
		
		return kit;
	}

	private static boolean containsSimilar(ItemStack[] armor, ItemStack item) {
		
		for(ItemStack i : armor)		
			if(i !=null)
				if(i.isSimilar(item))
					return true;
		
		return false;
	}

	public static int getClassLevel(Player p, String className) {
		
		FileConfiguration cfg = HeroynMobarena.getInstance().ConfigManagers.get("PlayersClassesLevels").getCustomConfig();
		int level = cfg.getInt(p.getUniqueId().toString()+"."+className);
		
		return level != 0 ? level : 1;
	}

	public static String getTimer(int gameTimer) {
		
		return  "§b" + String.format("%02d:%02d", 
			    TimeUnit.SECONDS.toMinutes(gameTimer),
			    TimeUnit.SECONDS.toSeconds(gameTimer) - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(gameTimer)));
	}
	
	
	
	
}
