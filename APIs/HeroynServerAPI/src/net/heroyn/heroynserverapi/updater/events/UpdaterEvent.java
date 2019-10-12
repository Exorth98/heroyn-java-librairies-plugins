package net.heroyn.heroynserverapi.updater.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import net.heroyn.heroynserverapi.updater.UpdateType;

public class UpdaterEvent extends Event
{
    private static final HandlerList handlers;
    private UpdateType _type;
    
    static {
        handlers = new HandlerList();
    }
    
    public UpdaterEvent(final UpdateType type) {
        this._type = type;
    }
    
    public UpdateType getType() {
        return this._type;
    }
    
    public HandlerList getHandlers() {
        return UpdaterEvent.handlers;
    }
    
    public static HandlerList getHandlerList() {
        return UpdaterEvent.handlers;
    }
}
