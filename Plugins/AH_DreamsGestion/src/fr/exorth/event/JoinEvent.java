package fr.exorth.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.exorth.SqlConnect;

public class JoinEvent implements Listener {
	
    private SqlConnect sql;
    
    public JoinEvent(SqlConnect sql) {
        this.sql = sql;
    }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e){
		
		Player p = e.getPlayer();
		if(!sql.hasAccount(p)){
			sql.createNewAccount(p);
		}

		
	}

}
