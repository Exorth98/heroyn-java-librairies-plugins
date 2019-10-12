package net.heroyn.heroynapi.utils;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_13_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.heroyn.heroynapi.Team.TeamUtils;
import net.heroyn.heroynapi.utils.Flags.FlagsEnum;

public class Utils {
	
	public static void changeKillerToLastDamager(Player player) {
		String lastDamagerName = Flags.readStringFlag(player, FlagsEnum.GODMODE.getId());
		if (lastDamagerName != null) {
			Player killer = Bukkit.getPlayer(lastDamagerName);
			if (killer != null && !killer.getName().equals(player.getName()) && !TeamUtils.areInTheSameTeam(killer, player)) {
				CraftPlayer craftPlayer = (CraftPlayer) player;
				craftPlayer.getHandle().killer = ((CraftPlayer) killer).getHandle();
			}
			Flags.removeFlag(player, FlagsEnum.LastDamager.getId());
		}
	}

}
