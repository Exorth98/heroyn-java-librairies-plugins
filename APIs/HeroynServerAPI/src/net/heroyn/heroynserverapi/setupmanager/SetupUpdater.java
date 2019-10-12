package net.heroyn.heroynserverapi.setupmanager;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.heroyn.heroynserverapi.cosmetics.gadget.cooldown.CooldownListener;
import net.heroyn.heroynserverapi.tasks.UpdateCosmeticClass;
import net.heroyn.heroynserverapi.tasks.UpdateEntityFollowTask;
import net.heroyn.heroynserverapi.tasks.UpdateLocationTask;
import net.heroyn.heroynserverapi.tasks.UpdateMorphTask;
import net.heroyn.heroynserverapi.tasks.UpdateSaisons;


public class SetupUpdater
{
    
    public SetupUpdater(final JavaPlugin javaPlugin) {
        Bukkit.getPluginManager().registerEvents((Listener)new UpdateEntityFollowTask(), javaPlugin);
        Bukkit.getPluginManager().registerEvents((Listener)new UpdateMorphTask(), javaPlugin);
        Bukkit.getPluginManager().registerEvents((Listener)new UpdateLocationTask(), javaPlugin);
        Bukkit.getPluginManager().registerEvents((Listener)new UpdateCosmeticClass(), javaPlugin);
        Bukkit.getPluginManager().registerEvents((Listener)new CooldownListener(), javaPlugin);
        Bukkit.getPluginManager().registerEvents((Listener) new UpdateSaisons(), javaPlugin);
    }
}
