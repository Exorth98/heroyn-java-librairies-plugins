package net.heroyn.heroynserverapi.tasks;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public class UpdateCosmeticClass implements Listener {

    @EventHandler
    public void updatePet(final UpdaterEvent updaterEvent) {
    	PlayerInfo.getPlayerInfos().values().forEach(prodigyPlayer -> {
            if (prodigyPlayer.getPet() != null && prodigyPlayer.getPet().getUpdateTime() != null && updaterEvent.getType() == prodigyPlayer.getPet().getUpdateTime()) {
                prodigyPlayer.getPet().update();
            }
        });
    }
	
	@EventHandler
	public void updatecos(final UpdaterEvent updaterEvent) {
		if (updaterEvent.getType() != UpdateType.TICK) {
			return;
		}
		
        PlayerInfo.getPlayerInfos().values().forEach(prodigyPlayer -> {
            if (prodigyPlayer.getParticle() != null) {
                if (prodigyPlayer.getParticle().getParticle() != null) {
                    if (prodigyPlayer.isMoving()) {
                    	prodigyPlayer.getPlayer().spawnParticle(prodigyPlayer.getParticle().getParticle(), prodigyPlayer.getPlayer().getLocation(), 1, 0.1, 0.1, 0.1, 0.03);
                    }
                    else {
                        prodigyPlayer.getParticle().update();
                    }
                }
                else {
                    prodigyPlayer.getParticle().update();
                }
            }
            return;
        });
        
		PlayerInfo.getPlayerInfos().values().forEach(prodigyPlayer2 -> {
			if (prodigyPlayer2.getMinion() != null) {
		    	prodigyPlayer2.getMinion().getVisible();
		    	prodigyPlayer2.getMinion().update();
				if (prodigyPlayer2.getMinion().visible) {
					prodigyPlayer2.getMinion().Update();
				}
			}
			prodigyPlayer2.addTime();
		});

	}

}
