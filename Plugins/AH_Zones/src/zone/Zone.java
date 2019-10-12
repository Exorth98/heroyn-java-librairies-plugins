package zone;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import fr.exorth.Zones;
import fr.exorth.util.ZonesUtils;

public class Zone {
	
	static FileConfiguration config = Zones.getInstance().getConfig();
	
	String name;
	Location pos1;
	Location pos2;
	String entrymsg;
	String exitmsg;
	
	
	public Zone() {}
	
	public Zone(String name) {
		
		this.name=name;
	}
	
	public Zone(String name, Location pos1, Location pos2, String entrymsg, String exitmsg) {
		
		this.name=name;
		this.pos1=pos1;
		this.pos2=pos2;
		this.entrymsg=entrymsg;
		this.exitmsg=exitmsg;
		
	}
	
	public static Zone getZone(String name) {
		
		Location pos1 = (Location) config.get(name + ".pos1");
		Location pos2 = (Location) config.get(name + ".pos2");
		
		String entrymsg = (String) config.get(name + ".entrymsg");
		String exitmsg = (String) config.get(name + ".exitmsg");
		
		Zone zone = new Zone(name,pos1,pos2,entrymsg,exitmsg);
		return zone;
	}
	
	public void saveZone() {
		
		completeZone();
		
		config.set(name+".pos1",pos1);
		config.set(name+".pos2",pos2);
		
		config.set(name+".entrymsg", entrymsg);
		config.set(name+".exitmsg", exitmsg);
		
		Zones.getInstance().saveConfig();
	}
	
	public void removeZone() {
		
		config.set(name, null);
		ZonesUtils.removeZone(name);
	}

	private void completeZone() {
		
		if(entrymsg==null) {entrymsg="§aTu entres dans la zone §b"+name;}
		if(exitmsg==null) {exitmsg="§aTu sors de la zone §b"+name;}
		
		if(pos1==null) {pos1=new Location(Bukkit.getWorlds().get(0),0.0,0.0,0.0);}
		if(pos2==null) {pos2=new Location(Bukkit.getWorlds().get(0),0.0,0.0,0.0);}
		
		ZonesUtils.completeZone(name);
	}
	
	public void setPos1(Location pos1) {
		
		this.pos1=pos1;
		saveZone();
		
	}
	
	public void setPos2(Location pos2) {
		
		this.pos2=pos2;
		saveZone();
		
	}
	
	public void setEntrymsg(String Entrymsg) {
		
		this.entrymsg=Entrymsg;
		saveZone();
	}
	
	public void setExitmsg(String Exitmsg) {
		
		this.exitmsg=Exitmsg;
		saveZone();
	}
	
	public Location getPos1() {
		
		return this.pos1;
	}
	
	public Location getPos2() {
		
		return this.pos2;
	}
	
	public String getEntrymsg() {
		
		return this.entrymsg;
	}
	
	public String getExitmsg() {
		
		return this.exitmsg;
	}

}
