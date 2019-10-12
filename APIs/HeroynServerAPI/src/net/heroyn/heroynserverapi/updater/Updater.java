package net.heroyn.heroynserverapi.updater;

import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import net.heroyn.heroynserverapi.updater.events.UpdaterEvent;

public class Updater implements Runnable
{
    private JavaPlugin _plugin;
    
    public Updater(final JavaPlugin plugin) {
        this._plugin = plugin;
        this._plugin.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this._plugin, (Runnable)this, 0L, 1L);
    }
    
    @Override
    public void run() {
        UpdateType[] array;
        for (int length = (array = UpdateType.class.getEnumConstants()).length, i = 0; i < length; ++i) {
            final UpdateType updateType = array[i];
            if (updateType.Elapsed()) {
                this._plugin.getServer().getPluginManager().callEvent((Event)new UpdaterEvent(updateType));
            }
        }
    }
}
