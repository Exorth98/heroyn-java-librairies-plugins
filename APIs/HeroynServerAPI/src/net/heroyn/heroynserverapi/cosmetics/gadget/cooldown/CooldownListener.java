package net.heroyn.heroynserverapi.cosmetics.gadget.cooldown;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import net.heroyn.heroynserverapi.nms.other.ActionBar;
import net.heroyn.heroynserverapi.updater.UpdateType;
import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;
import net.heroyn.heroynserverapi.utils.CC;

public class CooldownListener implements Listener
{
    @EventHandler
    public void updateEvent(final UpdaterEvent updaterEvent) {
        if (updaterEvent.getType() == UpdateType.SEC) {
            Cooldown.getCooldowns().values().forEach(list -> list.removeIf(cooldown -> {
                cooldown.updateTime();
                return cooldown.getTime() <= 0;
            }));
        }
        else if (updaterEvent.getType() == UpdateType.TICK) {
            Cooldown.getCooldowns().keySet().forEach(uuid -> Cooldown.getCooldowns().get(uuid).stream().filter(cooldown2 -> Bukkit.getPlayer(uuid) != null && Bukkit.getPlayer(uuid).getInventory().getItemInMainHand() != null && Bukkit.getPlayer(uuid).getInventory().getItemInMainHand().equals((Object)cooldown2.getItem())).findFirst().ifPresent(cooldown3 -> ActionBar.send(String.valueOf(cooldown3.getItem().getItemMeta().getDisplayName()) + " " + CC.red + String.valueOf(cooldown3.getTime()), Bukkit.getPlayer(uuid))));
        }
    }
}
