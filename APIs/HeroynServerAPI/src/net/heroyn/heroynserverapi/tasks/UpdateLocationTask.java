package net.heroyn.heroynserverapi.tasks;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public class UpdateLocationTask implements Listener
{
    @EventHandler
    public void UpdateEvent(final UpdaterEvent updaterEvent) {
        if (updaterEvent.getType() == UpdateType.SEC) {
            PlayerInfo.getPlayerInfos().values().stream().filter(playerInfo -> playerInfo.getPlayer() != null).forEach(prodigyPlayer2 -> prodigyPlayer2.setSecondPlayerLocation(prodigyPlayer2.getPlayer().getLocation()));
        }
        else if (updaterEvent.getType() == UpdateType.FASTEST) {
            for (final PlayerInfo prodigyPlayer3 : PlayerInfo.getPlayerInfos().values()) {
                if (prodigyPlayer3.getPlayer() != null) {
                    this.checkMove(prodigyPlayer3);
                }
            }
        }
    }
    
    private void checkMove(final PlayerInfo playerInfo) {
        final Location location = playerInfo.getPlayer().getLocation();
        Location location2 = playerInfo.getLastLocation();
        if (playerInfo.getLastLocation() == null) {
        	playerInfo.setLastLocation(location);
            location2 = playerInfo.getLastLocation();
        }
        playerInfo.setLastLocation(location);
        if (location2.getX() != location.getX() || location2.getZ() != location.getZ()) {
            if (!playerInfo.isMoving()) {
            	playerInfo.setMoving(true);
            }
        }
        else if (playerInfo.isMoving()) {
        	playerInfo.setMoving(false);
        }
    }
}
