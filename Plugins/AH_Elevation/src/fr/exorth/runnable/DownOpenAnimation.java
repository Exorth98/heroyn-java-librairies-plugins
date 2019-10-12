package fr.exorth.runnable;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import fr.exorth.Elevation;
import fr.exorth.util.ElevationUtils;

public class DownOpenAnimation extends BukkitRunnable{
	
	Location loc;
	Location pos;
	Location pos2;
	
	Player p;
	
	int count = 0;
	int finalcount;


	public DownOpenAnimation(Location underBlock, Player player, int finalcount) {
		this.loc=underBlock;
		this.pos = loc.clone().subtract(2,0,2);
		this.pos2 = pos.clone();
		this.p = player;
		this.finalcount = finalcount;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		
		if(count==0){
			
			
			for(int i=0 ; i<5 ; i++){
				
				for(int j=0 ; j<5 ; j++){
					
					if(ElevationUtils.inDisk2(pos)){
					
						if(i>0 && j>0 && i<4 && j<4){
							
							pos.getBlock().setTypeId(0,false);
							
						}else{
							pos.getBlock().setTypeIdAndData(95,(byte)3,false);
						}
					
					}
					
					pos.add(0,0,1);
					
				}
				pos.subtract(0,0,5);
				pos.add(1,0,0);
			}
			
			
		}
		
		if(count==1){
			
			for(int i=0 ; i<5 ; i++){
				
				for(int j=0 ; j<5 ; j++){
					
					if(ElevationUtils.inDisk2(pos2)){
						
						pos2.getBlock().setType(Material.STAINED_GLASS);
					
					}
					
					pos2.add(0,0,1);
					
				}
				pos2.subtract(0,0,5);
				pos2.add(1,0,0);
			}

			
		}
		
		if(count==finalcount){
			
			Elevation.getInstance().onDown.remove(p);
			this.cancel();
		}
		
		count++;
		
		
		
	}

}
