package net.heroyn.heroynserverapi.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class testCmd implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		/*
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		if (args[0].equalsIgnoreCase("pig")) {
			Entity ent = player.getWorld().spawnEntity(player.getLocation(), EntityType.PIG);
			Morph.test(ent, player);
			return true;
		}
		
		if (args[0].equalsIgnoreCase("test")) {
			magicChest chest = new magicChest(player, magicChestEnum.CHEST_COMMUN);
			chest.open();
			return true;
		}
		
		
		if (args[0].equalsIgnoreCase("halloween")) {
			HeroynServerAPI.isHalloween = !HeroynServerAPI.isHalloween;
			Bukkit.broadcastMessage("§6Halloween: " + (HeroynServerAPI.isHalloween ? "§aactivé" : "§cDesactivé"));
			return true;
		}
		
		if (args[0].equalsIgnoreCase("item")) {
			Item3D item = new Item3D(player, new ItemStack(Material.CHEST), "test");
			item.setPosition(player.getLocation(), 0F);
			item.move(true);
			return true;
		}
		
		*/
		return false;
	}

}
