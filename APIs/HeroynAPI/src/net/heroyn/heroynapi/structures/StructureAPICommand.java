package net.heroyn.heroynapi.structures;

import net.heroyn.heroynapi.utils.Cuboid;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class StructureAPICommand implements CommandExecutor {

    private final Map<UUID, Cuboid> regionMode = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if(!(sender instanceof Player)) return false;
        final Player player = (Player) sender;
        if(args[0].equalsIgnoreCase("region")){
            if(regionMode.contains(player.getUniqueId())){
                regionMode.remove(player.getUniqueId());
            }else{

            }
            return true;
        }
        if(args[0].equalsIgnoreCase("generatejson")){
            return true;
        }
        return false;
    }

}
