package net.heroyn.mobarena.classe;

import net.heroyn.mobarena.classe.classes.*;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum ClassesInfos {
	
	MAGE	(  "Mage"   , Material.GOLD_SWORD		, Mage.class	),
	TANK	(  "Tank"   , Material.IRON_SWORD		, Tank.class	),
	KNIGHT	(  "Knight" , Material.DIAMOND_SWORD	, Knight.class	),
	RANGER	(  "Ranger" , Material.BOW				, Ranger.class	);
	
	private String name;
	private Material symbol;
	private Class<? extends ClasseBase> javaCalss;
	
	private ClassesInfos(String name, Material symbol, Class<? extends ClasseBase> javaCalss) {
		this.name = name;
		this.symbol = symbol;
		this.javaCalss = javaCalss;
	}

	public String getName() {
		return name;
	}

	public Material getSymbol() {
		return symbol;
	}

	public Class<? extends ClasseBase> getJavaCalss() {
		return javaCalss;
	}
	
	public static ClassesInfos getInfos(String name) {
		
		for(ClassesInfos info : ClassesInfos.values())
			if(info.getName().equalsIgnoreCase(name))
				return info;
		
		return null;
	}
	
	public static ClasseBase getClasseBase(String name, Player p, int level) {
		
		try {
			ClasseBase sel = getInfos(name).getJavaCalss().getConstructor(Player.class, Integer.class).newInstance(p,level);
			return sel;
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	

}
