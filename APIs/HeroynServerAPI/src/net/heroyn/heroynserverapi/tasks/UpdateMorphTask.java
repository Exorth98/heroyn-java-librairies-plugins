package net.heroyn.heroynserverapi.tasks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public class UpdateMorphTask implements Listener
{
    @EventHandler
    public void updateMorph(final UpdaterEvent updaterEvent) {
    	/**
    	 * 
    	 
        PlayerInfo.getPlayerInfos().values().forEach(prodigyPlayer -> {
            if (prodigyPlayer.getMorph() != null && prodigyPlayer.getMorph().getUpdateTime() != null && updaterEvent.getType() == prodigyPlayer.getMorph().getUpdateTime()) {
                prodigyPlayer.getMorph().update();
            }
        });
        */
    }
}
