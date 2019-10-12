package net.heroyn.heroynserverapi.magicchest;

public enum RarityEnum {
	
	COMMUN("§9[§7Commun§9] §r"),
	RARE("§9[§1Rare§9] §r"),
	LEGENDAIRE("§9[§dLegendaire§9] §r"),
	EPIQUE("§9[§6Epique§9] §r"),
	NOEL("§9[§cNo"+"Ë".toLowerCase()+"l§9] §r"),
	HALLOWEEN("§9[§5Halloween§9] §r");
	
	public String prefix;
	
	private RarityEnum(String prefix) {
		this.prefix = prefix;
	}
	
	public String getPrefix() {
		return prefix;
	}

}
