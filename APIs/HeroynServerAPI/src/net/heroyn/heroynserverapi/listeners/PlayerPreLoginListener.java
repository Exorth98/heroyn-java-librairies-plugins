package net.heroyn.heroynserverapi.listeners;

import net.heroyn.heroynserverapi.commands.MaintenanceCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class PlayerPreLoginListener implements Listener {

    @EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e){
        Player player = Bukkit.getPlayer(e.getName());
        if(MaintenanceCommand.isMaintenance()){
            if(!player.isOp() || !player.hasPermission("heroyn.maintenance.bypass")){
                e.setKickMessage("§cLe serveur est en maintenance...\n\n\nMerci de votre compréantion");
                e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_FULL);
            }
        }
    }

}
