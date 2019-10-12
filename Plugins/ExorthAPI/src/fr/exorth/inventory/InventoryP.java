package fr.exorth.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryP {
	
	private Inventory inv;
	
	public InventoryP(Inventory inv) {
		
		this.inv=inv;
	}
	
	
	
	public Inventory getInventory() {
		
		return this.inv;
	}
	
	public ItemStack getItem(int line,int row) {
		
		return inv.getItem(((line-1)*9) + row - 1);
		
	}
	
	public void setItem(ItemStack item, int line, int row ) {
		
		inv.setItem(((line-1)*9) + row - 1, item);
	}
	
	public void fill(ItemStack item, boolean replace) {
		
		for(int i=0;i<inv.getSize();i++) {
			
			if(!replace) {
				
				if(inv.getItem(i).getType()==Material.AIR) {
					
					inv.setItem(i, item);
				}
				
			}else {
				inv.setItem(i, item);
			}
			
			
		}
		
	}
	
	public void fillRow(ItemStack item, int row , boolean replace) {
		
		for(int i = 0; i< this.inv.getSize();i++) {
			
			int rowNbr = (i%9)+1;
			
			if(row == rowNbr) {
				
				if(!replace) {
					
					if(inv.getItem(i).getType()==Material.AIR) {
						
						inv.setItem(i, item);
					}
					
				}else {
					inv.setItem(i, item);
				}
				
			}
			
		}
		
	}
	
	public void fillLine(ItemStack item, int line , boolean replace) {
		
		for(int i = 0; i< this.inv.getSize();i++) {
			
			int lineNbr = (i/9)+1;
			
			if(line == lineNbr) {
				
				if(!replace) {
					
					if(inv.getItem(i).getType()==Material.AIR) {
						
						inv.setItem(i, item);
					}
					
				}else {
					inv.setItem(i, item);
				}
				
			}
			
		}
		
	}
	
	

}
