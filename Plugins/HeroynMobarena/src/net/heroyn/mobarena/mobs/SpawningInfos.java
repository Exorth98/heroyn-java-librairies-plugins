package net.heroyn.mobarena.mobs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawningInfos {
	
	private Map<Integer, Integer> amounts = new HashMap<>();
	private String spawningCode;
	
	private final int maxWave = 200;
	
	public SpawningInfos(String spawningCode) {
		this.spawningCode = spawningCode;
		createAmounts();
	}

	private void createAmounts() {
		
		List<String> infos = new ArrayList<>();
		
		if(spawningCode.equals("X")) return;
		
		if(spawningCode.contains("&&"))
			for(String code : spawningCode.split("&&"))
				infos.add(code);
		else infos.add(spawningCode);
		
		for(String Info : infos) {
			// Info Format = [1-20]:20
			//	  or		 [1-20]:20/2			
			String interval = Info.split(":")[0];  // [1-20]
			String amounts  = Info.split(":")[1];  // 20 or 20/2
			
			interval = interval.substring(1, interval.length()-1); // 1-20
			
			String beg = interval.split("-")[0];
			String e = interval.split("-")[1];
			
			int begin 		= Integer.parseInt(beg); // 1
			int end   		= Integer.parseInt(e); // 20
			int waveDiff 	= end - begin + 1;  // 20
			
			int max = 0;
			int min = 1;
			
			if(amounts.contains("/")) { // 20/2
				max = Integer.parseInt(amounts.split("/")[0]); // 20
				min = Integer.parseInt(amounts.split("/")[1]); // 2
			}
			else{  // 20
				max = Integer.parseInt(amounts); // 20
				min = max;  // 20
			}
			
			int diff= max-min;  // 18 or 0
			double toAdd = (double)diff/(double)waveDiff; // 0 or 18/20
			
			int j=1;
			for(int i = begin; i <= end; i++, j++){
				
				int amount = max - (int)((double)j*(double)toAdd);
				this.amounts.put(i, amount);
			}
			for(int i = end+1; i <= maxWave; i++) {
				this.amounts.put(i, 0);
			}
		}

		
		for(int i = 0; i < maxWave; i++)
			if( new ArrayList<Integer>(amounts.keySet()).get(i) != i)
				amounts.put(i, 0);
		
	}
	
	public Map<Integer,Integer> getAmounts(){
		return this.amounts;
	}
	
}
