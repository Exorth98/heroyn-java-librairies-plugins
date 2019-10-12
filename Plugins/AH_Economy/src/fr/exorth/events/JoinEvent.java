package fr.exorth.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.exorth.AHEconomy;
import net.milkbowl.vault.economy.Economy;

public class JoinEvent implements Listener {
	
	public Economy economy = AHEconomy.getInstance().economy;
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		if(AHEconomy.getInstance().setupEconomy()){
			
			Player p = e.getPlayer();
			
			if(!economy.hasAccount(p)){
				economy.depositPlayer(p, 1500.00);
			}
			
			p.sendMessage("§czofpz");
			
		}
		
	}

}
