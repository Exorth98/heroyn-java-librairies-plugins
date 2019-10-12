package net.heroyn.heroynapi.virtualmenu;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynapi.SpecialItem.MenuItem;

public abstract class VirtualMenu
{
  public String title;
  public int size;
  public Inventory inventory;
  static Map<Integer, VirtualMenu> allVirtualMenu = new HashMap<>();
  static Map<Player, VirtualMenu> openVirtualMenus = new HashMap<>();
  public Map<Integer, MenuItem> myItems = new HashMap<>();
  
  public VirtualMenu(String title, int size)
  {
    this.title = title;
    this.size = size;
  }
  
  public abstract Inventory getInventory();
  
  public void open(Player p) {
	  
	  p.closeInventory();
	  openVirtualMenus.put(p, this);
	  p.openInventory(getInventory());
  }
  
  public abstract void onOpen();
  
  public int setAndRegisterInventory(VirtualMenu menu)
  {
    int result = allVirtualMenu.size() + 1;
    allVirtualMenu.put(Integer.valueOf(result), menu);
    return result;
  }
  
 /* public void addAndRegisterItem(int pos, MenuItem item)
  {
    this.myItems.put(Integer.valueOf(pos), item);
  }*/
  
  public void addMenuItem(MenuItem item)
  {
    getInventory().addItem(new ItemStack[] { item.getItems() });
    this.myItems.put(getInventory().firstEmpty(), item);
  }
  
  public void setMenuItem(int pos, MenuItem item)
  {
    getInventory().setItem(pos, item.getItems());
    this.myItems.put(Integer.valueOf(pos), item);
  }
  
  public void addItem(ItemStack item)
  {
    getInventory().addItem(new ItemStack[] { item });
  }
  
  public void setItem(int pos, ItemStack item)
  {
    getInventory().setItem(pos, item);
  }
  
  public void fill(ItemStack item, boolean replace) {
		
		for(int i=0;i<inventory.getSize();i++) {
			
			if(!replace) {
				
				if(inventory.getItem(i).getType()==Material.AIR) {
					
					inventory.setItem(i, item);
				}
				
			}else {
				inventory.setItem(i, item);
			}			
		}	
  }
  public void fill(MenuItem item, boolean replace) {
		
		for(int i=0;i<inventory.getSize();i++) {
			
			if(!replace) {
				
				if(inventory.getItem(i).getType()==Material.AIR) {
					
					setMenuItem(i, item);
				}
				
			}else {
				setMenuItem(i, item);
			}			
		}	
  }
	
  public void fillRow(ItemStack item, int row , boolean replace) {
		
		for(int i = 0; i< this.inventory.getSize();i++) {
			
			int rowNbr = (i%9)+1;
			
			if(row == rowNbr) {
				
				if(!replace) {
					
					if(inventory.getItem(i).getType()==Material.AIR) {
						
						inventory.setItem(i, item);
					}
					
				}else {
					inventory.setItem(i, item);
				}
				
			}
			
		}
		
  }
  public void fillRow(MenuItem item, int row , boolean replace) {
		
		for(int i = 0; i< this.inventory.getSize();i++) {
			
			int rowNbr = (i%9)+1;
			
			if(row == rowNbr) {
				
				if(!replace) {
					
					if(inventory.getItem(i).getType()==Material.AIR) {
						
						setMenuItem(i, item);
					}
					
				}else {
					setMenuItem(i, item);
				}
				
			}
			
		}
		
  }
  
  public void fillLine(ItemStack item, int line , boolean replace) {
		
		for(int i = 0; i< this.inventory.getSize();i++) {
			
			int lineNbr = (i/9)+1;
			
			if(line == lineNbr) {			
				if(!replace) {
					
					if(inventory.getItem(i).getType()==Material.AIR) {
						
						inventory.setItem(i, item);
					}
					
				}else {
					inventory.setItem(i, item);
				}
				
			}
			
		}
		
  }
  public void fillLine(MenuItem item, int line , boolean replace) {
		
		for(int i = 0; i< this.inventory.getSize();i++) {
			
			int lineNbr = (i/9)+1;
			
			if(line == lineNbr) {			
				if(!replace) {
					
					if(inventory.getItem(i).getType()==Material.AIR) {
						
						setMenuItem(i, item);
					}
					
				}else {
					setMenuItem(i, item);
				}
				
			}
			
		}
		
  }
  
  public String getTitleWithExtraText(String text)
  {
    return getTitle() + text;
  }
  
  public int getSize()
  {
    return this.size * 9;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public int getPos(int line, int slot)
  {
    return line * 9 - 9 + slot - 1;
  }
  
  public Map<Integer, MenuItem> getMyItems()
  {
    return this.myItems;
  }
  
  public static Map<Integer, VirtualMenu> getAllVirtualMenu()
  {
    return allVirtualMenu;
  }
  
  public static Map<Player,VirtualMenu> getOpenVirtualMenusMap()
  {
    return openVirtualMenus;
  }

  public static void closeMenu(Player p) {
		
	openVirtualMenus.remove(p);	
		
  }
}
