package fr.exorth.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;

import net.minecraft.server.v1_12_R1.Entity;





public class npcCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

        if(sender instanceof Player){
            
            //variables
            Player joueur = (Player) sender;
            Location loc = joueur.getLocation();
            Villager npc = (Villager) loc.getWorld().spawnEntity(loc, EntityType.VILLAGER);
            Entity nmsVillager = ((CraftEntity) npc).getHandle();
           
            //custom le mob nms
            nmsVillager.setCustomName("§a§lHôtel des ventes");
            nmsVillager.setCustomNameVisible(true);
            nmsVillager.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
           
            //desactiver l'ai
            npc.setAI(false);
           
           
           
        }
		
		return false;
	}

}
