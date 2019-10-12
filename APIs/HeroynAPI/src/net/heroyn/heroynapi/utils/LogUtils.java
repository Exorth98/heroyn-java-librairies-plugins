package net.heroyn.heroynapi.utils;

import org.bukkit.Bukkit;

public class LogUtils {
	
	public static void Log(String msg) {
		Bukkit.getConsoleSender().sendMessage("§9[§eLog-HeroynAPI§9]§7-> " + msg);
	}


	public static void LogAlert(String msg) {
		Bukkit.getConsoleSender().sendMessage("§9[§eLog-HeroynAPI-§6Alert§9]§7-> " + msg);
	}
	
	public static void LogErreur(String msg) {
		Bukkit.getConsoleSender().sendMessage("§9[§eLog-HeroynAPI-§CErreur§9]§7-> " + msg);
	}
	
	public static void LogSuccess(String msg) {
		Bukkit.getConsoleSender().sendMessage("§9[§eLog-HeroynAPI-§aSucces§9]§7-> " + msg);
	}
}
