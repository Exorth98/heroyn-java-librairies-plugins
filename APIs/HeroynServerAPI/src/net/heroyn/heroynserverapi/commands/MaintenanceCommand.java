package net.heroyn.heroynserverapi.commands;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.maintenance.MaintenanceState;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MaintenanceCommand implements CommandExecutor {


    public static ConfigManager CONFIG;
    public static MaintenanceState STATE;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {


        if(sender instanceof Player){
            Player player = (Player)sender;
            if(!CONFIG.getCustomConfig().contains("maintenance")){
                CONFIG.getCustomConfig().set("maintenance", new MaintenanceState(false, ""));
            }

            if(args.length == 0){
                if(isMaintenance()){ //Maintenance activer
                    //désactivation de la maintenance
                    setMaintenance(new MaintenanceState(false, player.getName()));
                    player.sendMessage("§aLa maintenance à correctement été désactivé !");
                }else{//Maintenance désactiver
                    //activation de la maintenance
                    setMaintenance(new MaintenanceState(true, player.getName()));
                    player.sendMessage("§aLa maintenance à correctement été activé !");

                    for(Player players : Bukkit.getOnlinePlayers()){
                        if(!players.isOp() || !players.hasPermission("heroyn.maintenance.bypass")){
                            players.kickPlayer("§cLe serveur est en maintenance...\n\n\nMerci de votre compréantion");
                        }
                    }

                }
                return false;
            }

        }
        return false;
    }

    private void setMaintenance(MaintenanceState state){
        STATE = state;
        CONFIG.getCustomConfig().set("maintenance", state);
        CONFIG.saveCustomConfig();
    }

    public static boolean isMaintenance(){
        return STATE.isMaintenance();
    }

}
