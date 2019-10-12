package net.heroyn.heroynserverapi.updatesystem.items;

import net.heroyn.heroynapi.SpecialItem.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemDecoration extends MenuItem{

    public ItemDecoration(short meta) {
        this(" ", new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, meta));
    }

    public ItemDecoration(String name, ItemStack stack) {
        super(name, new String[0], stack);
    }

    @Override
    public void onUse(Player player) {

    }

}
