package fr.exorth.runnable;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.util.TirageUtils;

public class AnimationRunnable extends BukkitRunnable {
	
	List<ItemStack> List;
	Player p;
	Inventory Tirage;
	
	int ind = 0;
	double count = 1;

	public AnimationRunnable(Player p, Inventory Tirage) {
		
		this.p = p;
		this.Tirage=Tirage;
		this.count = 1;
		this.ind = 0;
		this.List = TirageUtils.getList();
	}

	@Override
	public void run() {
		
		
		if(count<=30){TirageUtils.roll(List,Tirage,ind);addOnetoInd();}
		else if(count<=60 && ((count -30) % 3) == 0){TirageUtils.roll(List,Tirage,ind);addOnetoInd();}
		else if(count<=80 && ((count -60) % 5) == 0){TirageUtils.roll(List,Tirage,ind);addOnetoInd();}
		else if(count<=100 && ((count -80) % 10) == 0){TirageUtils.roll(List,Tirage,ind);addOnetoInd();}
		else if(count>100){this.cancel();TirageUtils.end(Tirage,p);}
		
		count++;

	}
	
	private void addOnetoInd() {
		if(ind == List.size()-9){
			ind =0;
		}else{
			ind++;
		}
		
	}

}
