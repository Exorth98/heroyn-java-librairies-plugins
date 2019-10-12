package net.heroyn.heroynserverapi.tasks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public class UpdateEntityFollowTask implements Listener
{
    @EventHandler
    public void followTask(final UpdaterEvent updaterEvent) {
        if (updaterEvent.getType() != UpdateType.TICK) {
            return;
        }
        PlayerInfo.getPlayerInfos().values().forEach(prodigyPlayer -> prodigyPlayer.getEntityFollowList().forEach(entityFollow -> entityFollow.follow(prodigyPlayer.getPlayer())));
    }
}
