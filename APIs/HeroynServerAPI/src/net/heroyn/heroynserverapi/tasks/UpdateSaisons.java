package net.heroyn.heroynserverapi.tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public class UpdateSaisons implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void UpdateNoel(UpdaterEvent event) {
		if (event.getType().equals(UpdateType.MIN_64)) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM");
			Calendar now = Calendar.getInstance();
			Calendar noelBegin = Calendar.getInstance();
			Calendar noelEnd = Calendar.getInstance();
			try {
				noelBegin.setTime(sdf.parse("24-12"));
				noelEnd.setTime(sdf.parse("28-12"));
				if ((now.getTime().getDay() == noelBegin.getTime().getDay())
						&& (now.getTime().getMonth() == noelBegin.getTime().getMonth())) {
					HeroynServerAPI.isNoel = true;
				}
				if ((now.getTime().getDay() == noelEnd.getTime().getDay())
						&& (now.getTime().getMonth() == noelEnd.getTime().getMonth())) {
					HeroynServerAPI.isNoel = false;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	
}
