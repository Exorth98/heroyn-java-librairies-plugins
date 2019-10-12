package net.heroyn.heroynserverapi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.Bukkit;

public class ServerAnniversary {


	public static Calendar dateAnniversary() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		try {
			cal.setTime(sdf.parse("14-04-2018"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return cal;
	}
	
	public static Calendar now() {
		Calendar cal = Calendar.getInstance();
		return cal;
	}
	
	@SuppressWarnings("deprecation")
	public static void test() {
		if (dateAnniversary().getTime().getDay() == now().getTime().getDay()) {
			Log("DAY OK");
			if (dateAnniversary().getTime().getMonth() == now().getTime().getMonth()) {
				Log("MONTH OK");
			}
		}
	}
	
	public static void Log(String msg) {
		Bukkit.getConsoleSender().sendMessage("§9[§eLog-HeroynAPI§9]§7-> " + msg);
	}
}
