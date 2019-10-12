package net.heroyn.heroynserverapi.updatesystem.items;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynserverapi.updatesystem.Update;
import org.bukkit.entity.Player;

public class ItemUpdate extends MenuItem{

    public ItemUpdate(Update update) {
        super("§a"+update.getName().replace("&", "§"), update.getDescription(), update.getItemStack());
    }

    @Override
    public void onUse(Player player) {

    }

}
