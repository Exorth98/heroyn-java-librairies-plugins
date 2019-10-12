 package net.heroyn.heroynapi.customevents;
 
 import org.bukkit.entity.Player;
 import org.bukkit.event.Event;
 import org.bukkit.event.HandlerList;
 
 public class PlayerChangeServerEvent extends Event
 {
   private static final HandlerList handlers = new HandlerList();
   Player player;
   String serverName;
   
   public PlayerChangeServerEvent(Player player, String serverName)
   {
     this.player = player;
     this.serverName = serverName;
   }
   
   public Player getPlayer()
   {
     return this.player;
   }
   
   public String getServerName() {
     return this.serverName;
   }
   

   public HandlerList getHandlers()
   {
     return handlers;
   }
   
   public static HandlerList getHandlerList()
   {
     return handlers;
   }
 }
