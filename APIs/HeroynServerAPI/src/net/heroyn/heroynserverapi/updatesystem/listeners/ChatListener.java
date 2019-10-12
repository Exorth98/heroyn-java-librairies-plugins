package net.heroyn.heroynserverapi.updatesystem.listeners;

import net.heroyn.heroynserverapi.updatesystem.ChatCommandPlayer;
import net.heroyn.heroynserverapi.updatesystem.VirtualMenuUpdateSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class ChatListener implements Listener{

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(ChatCommandPlayer.contains(e.getPlayer())){
            ChatCommandPlayer chatPlayer = ChatCommandPlayer.byPlayer(e.getPlayer());
            assert chatPlayer != null;
            if(chatPlayer.getState() == ChatCommandPlayer.EnumType.NAME){
                final String name = e.getMessage();
                if(VirtualMenuUpdateSystem.contains(name)){
                    chatPlayer.getPlayer().sendMessage("§cCette mise à jour existe déjà");
                    e.setCancelled(true);
                    return;
                }
                chatPlayer.getData().put("name", name);
                chatPlayer.setState(ChatCommandPlayer.EnumType.DESC);
                chatPlayer.getPlayer().sendMessage(" ");
                chatPlayer.getPlayer().sendMessage("§aNom enregistré ! §b("+"§a"+name.replace("&", "§")+"§b)");
                chatPlayer.getPlayer().sendMessage("§eVeuillez saisir la première ligne de la description");
                e.setCancelled(true);
            }else if(chatPlayer.getState() == ChatCommandPlayer.EnumType.DESC){
                if(!chatPlayer.getData().containsKey("desc")){
                    final List<String> desc = new ArrayList<>();
                    if(e.getMessage().equalsIgnoreCase("none")){
                        desc.add(" ");
                    }else{
                        desc.add("§7"+e.getMessage().replace("&", "§"));
                    }
                    chatPlayer.getData().put("desc", desc);
                    chatPlayer.getPlayer().sendMessage(" ");
                    chatPlayer.getPlayer().sendMessage("§aPour confirmer la mise à jour, veuillez faire la commande §b/maj finish §aou continuez");
                    chatPlayer.getPlayer().sendMessage("§aLigne 1 ajouté avec succès !");
                    e.setCancelled(true);
                    return;
                }
                final List<String> desc = (List<String>) chatPlayer.getData().get("desc");
                if(e.getMessage().equalsIgnoreCase("none")){
                    desc.add(" ");
                }else{
                    desc.add("§7"+e.getMessage().replace("&", "§"));
                }
                chatPlayer.getPlayer().sendMessage("§aLigne "+desc.size()+" ajouté avec succès !");
                e.setCancelled(true);
            }
        }
    }

}
