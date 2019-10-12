package net.heroyn.heroynserverapi.maintenance;

import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

public class MaintenanceState implements ConfigurationSerializable {

    private boolean state;
    private String lastUser;

    public MaintenanceState(boolean state, String lastUser) {
        this.state = state;
        this.lastUser = lastUser;
    }


    public boolean isMaintenance() {
        return state;
    }

    public void setMaintenance(boolean state) {
        this.state = state;
    }

    public String getLastUser() {
        return lastUser;
    }

    public void setLastUser(String lastUser) {
        this.lastUser = lastUser;
    }

    @Override
    public Map<String, Object> serialize() {
        final Map<String, Object> map = new HashMap<>();
        map.put("state", state);
        map.put("lastUser", lastUser);
        return map;
    }

}
