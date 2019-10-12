package net.heroyn.heroynapi.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.HeroynAPI;
import net.heroyn.heroynapi.SpecialItem.MenuItem;
import net.heroyn.heroynapi.SpecialItem.MenuItemInterface;
import net.heroyn.heroynapi.virtualmenu.VirtualMenu;

public class MenuItemListener
        implements Listener
{
    HeroynAPI pl;

    public MenuItemListener(HeroynAPI pl)
    {
        this.pl = pl;
    }

    @EventHandler
    public void MenuItemInteract(PlayerInteractEvent event)
    {

        ItemStack item = event.getItem();
        Action a = event.getAction();
        Player player = event.getPlayer();
        if (item == null) {
            return;
        }
        for (MenuItem menuItem : MenuItem.getAllMenuItem()) {
            if (((menuItem instanceof MenuItemInterface)) && (item.isSimilar(menuItem.getItems())))
            {
                event.setCancelled(true);
                if ((menuItem.ItemIsVip())) //&& ( regarder le grade du joeur)
                {
                    player.sendMessage("§cVous devez être Vip!");
                    return;
                }
                if ((a.equals(Action.RIGHT_CLICK_AIR)) || (a.equals(Action.RIGHT_CLICK_BLOCK)))
                {
                    menuItem.InteractOnUse(player);
                    return;
                }
                if ((a.equals(Action.LEFT_CLICK_AIR)) || (a.equals(Action.LEFT_CLICK_BLOCK)))
                {
                    menuItem.InteractLeftOnUse(player);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void VirtualMenuClose(InventoryCloseEvent event)
    {
        if(event.getPlayer() instanceof Player) {

            Player p = (Player) event.getPlayer();

            if(VirtualMenu.getOpenVirtualMenusMap().keySet().contains(p)) {

                VirtualMenu.closeMenu(p);

            }
        }
    }

    @EventHandler
    public void VirtualMenuClickSecurity(InventoryClickEvent event)
    {

        if(event.getSlotType() == SlotType.OUTSIDE ) return;

        ItemStack item = event.getCurrentItem();
        Player player = (Player)event.getWhoClicked();
        InventoryView invView = player.getOpenInventory();
        Inventory openInv = invView.getTopInventory();

        if (item == null || openInv.equals(player.getInventory())) {return;}

        if(VirtualMenu.getOpenVirtualMenusMap().keySet().contains(player)) {

            event.setCancelled(true);
        }


    }



    @EventHandler
    public void MenuItemInventoryInteract(InventoryClickEvent event)
    {
        if(event.getSlotType() == SlotType.OUTSIDE)return;

        ItemStack item = event.getCurrentItem();
        Player player = (Player)event.getWhoClicked();
        if (item == null) {
            return;
        }

        if(VirtualMenu.getOpenVirtualMenusMap().keySet().contains(player)) {

            VirtualMenu vm = VirtualMenu.getOpenVirtualMenusMap().get(player);

            if(vm.getMyItems().containsKey(event.getSlot())) {

                MenuItem menuItem = vm.getMyItems().get(event.getSlot());

                event.setCancelled(true);
                if ((menuItem.ItemIsVip()))  //&& ( regarder le grade du joeur)
                {
                    player.sendMessage("§cVous devez être Vip!");
                    return;
                }
                if (event.isRightClick())
                {
                    menuItem.RightClick(player);
                    return;
                }
                if (event.isLeftClick())
                {
                    menuItem.onUse(player);
                    return;
                }

            }
            else {

                for (MenuItem menuItem : MenuItem.getAllMenuItem()) {
                    if (((menuItem instanceof MenuItemInterface)) && (item.isSimilar(menuItem.getItems())))
                    {
                        event.setCancelled(true);
                        if ((menuItem.ItemIsVip()))  //&& ( regarder le grade du joeur)
                        {
                            player.sendMessage("§cVous devez être Vip!");
                            return;
                        }
                        if (event.isRightClick())
                        {
                            menuItem.RightClick(player);
                            return;
                        }
                        if (event.isLeftClick())
                        {
                            menuItem.onUse(player);
                            return;
                        }
                    }

                }



            }

        }

    }

}
