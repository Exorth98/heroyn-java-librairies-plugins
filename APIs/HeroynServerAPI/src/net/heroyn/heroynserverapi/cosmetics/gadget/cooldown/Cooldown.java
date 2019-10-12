package net.heroyn.heroynserverapi.cosmetics.gadget.cooldown;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;

@SuppressWarnings("rawtypes")
public class Cooldown
{
    private static Map<UUID, List<Cooldown>> cooldowns;
	private Class gadget;
    private int time;
    private ItemStack item;
    
    static {
        Cooldown.cooldowns = new HashMap<UUID, List<Cooldown>>();
    }
    
    public Cooldown(final Class gadget, final int time, final ItemStack item) {
        this.time = time;
        this.gadget = gadget;
        this.item = item;
    }
    
    public void updateTime() {
        --this.time;
    }
    
    public int getTime() {
        return this.time;
    }
    
    public Class getGadget() {
        return this.gadget;
    }
    
    public static Map<UUID, List<Cooldown>> getCooldowns() {
        return Cooldown.cooldowns;
    }
    
    public ItemStack getItem() {
        return this.item;
    }
}
