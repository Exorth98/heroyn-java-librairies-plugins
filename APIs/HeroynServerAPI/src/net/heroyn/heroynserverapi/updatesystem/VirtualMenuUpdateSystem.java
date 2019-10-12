package net.heroyn.heroynserverapi.updatesystem;

import net.heroyn.heroynapi.virtualmenu.VirtualMenu;
import net.heroyn.heroynserverapi.updatesystem.items.ItemDecoration;
import net.heroyn.heroynserverapi.updatesystem.items.ItemUpdate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class VirtualMenuUpdateSystem extends VirtualMenu{

    public static final List<Update> UPDATES = new ArrayList<>();

    public static boolean contains(String name){
        for (Update update : UPDATES) {
            if(update.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public static Update getUpdateByName(String name){
        for (Update update : UPDATES) {
            if(update.getName().equalsIgnoreCase(name)) return update;
        }
        return null;
    }

    public VirtualMenuUpdateSystem() {
        super("§8Mises à jours à venir", 1);;
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) {
            inventory = Bukkit.createInventory(null, getSize(), getTitle());

            setMenuItem(getPos(1, 1), new ItemDecoration((short)7));
            setMenuItem(getPos(1, 9), new ItemDecoration((short)7));

            for(int i = 0; i < UPDATES.size(); i++){
                if(i > 6) return inventory;
                setMenuItem(getPos(1, i+2), new ItemUpdate(UPDATES.get(i)));
            }
        }
        return inventory;
    }

    @Override
    public void onOpen() {

    }

}
