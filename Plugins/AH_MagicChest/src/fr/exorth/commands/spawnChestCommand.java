package fr.exorth.commands;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftArmorStand;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.EnderChest;

import fr.exorth.MagicChest;
import fr.exorth.util.Utils;
import net.minecraft.server.v1_12_R1.EntityArmorStand;

public class spawnChestCommand implements CommandExecutor {
	
	
	public FileConfiguration config = MagicChest.getInstance().getConfig();
	
	@SuppressWarnings("unchecked")
	ArrayList<Location> chestsLocs = (ArrayList<Location>) config.getList("chestslocs");
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player){
			
			Player p = (Player) sender;
			
			if(cmd.getName().equalsIgnoreCase("spawnmagicchest")){
				
				if(args.length==0){
					
					if(chestsLocs ==null){chestsLocs = new ArrayList<Location>();}				
					chestsLocs.add(p.getLocation().getBlock().getLocation());
					config.set("chestslocs", chestsLocs);
					MagicChest.getInstance().saveConfig();
					
					BlockFace dir = Utils.getDir(p);
					
					Location loc = p.getLocation();					
					loc.getBlock().setType(Material.ENDER_CHEST);
					
					ArmorStand stand = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
					EntityArmorStand as = ((CraftArmorStand)stand).getHandle();
					
					as.setNoGravity(true);
					as.setSmall(true);
					as.setInvulnerable(true);
					as.setInvisible(true);
					
					as.setCustomNameVisible(true);
					as.setCustomName("§a§lCoffre Magique");
					
					Block block = p.getLocation().getBlock();
					BlockState state = block.getState();
					EnderChest enderchest = new EnderChest(dir);
					state.setData(enderchest);
					state.update();
					
					p.sendMessage("§3Coffre Magique Créé !");
					
					
				}else{
					p.sendMessage("§Syntaxe correcte : /spawnmagicchest");
				}
				
			}
			
			
		}else{
			sender.sendMessage("§cCommande resérvée aux joueurs");
		}
		
		return false;
	}

}
