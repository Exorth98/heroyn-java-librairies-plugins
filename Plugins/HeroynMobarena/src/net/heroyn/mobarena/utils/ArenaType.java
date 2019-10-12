package net.heroyn.mobarena.utils;

public enum ArenaType {
	
	CUBOID , CYL ;

	
	
	public static ArenaType getType(String type) {
		
		switch(type) {
		
		case "CUBOID":
			return ArenaType.CUBOID;
		
		case "CYL":
			 return ArenaType.CYL;
		
		}
		
		return null;
	}
	
	public String toString() {
		
		if(this == ArenaType.CUBOID) return "CUBOID";
		else return "CYL";
	}

}
