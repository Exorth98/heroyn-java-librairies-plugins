package net.heroyn.heroynserverapi.magicchest;

public enum magicChestEnum {
	
	CHEST_COMMUN("§7commun", RarityEnum.COMMUN),
	CHEST_RARE("§1rare",RarityEnum.COMMUN, RarityEnum.RARE),
	CHEST_LEGENDAIRE("§dlegendaire",RarityEnum.COMMUN, RarityEnum.RARE, RarityEnum.LEGENDAIRE),
	CHEST_EPIQUE("§6epique", RarityEnum.COMMUN, RarityEnum.RARE, RarityEnum.LEGENDAIRE, RarityEnum.EPIQUE),
	CHEST_NOEL("§cnoel", RarityEnum.NOEL),
	CHEST_HALLOWEEN("§5halloween", RarityEnum.HALLOWEEN);
	
	public String chestName;
	public RarityEnum[] rarity;
	
	magicChestEnum(String chestName, RarityEnum... rarity) {
		this.chestName = chestName;
		this.rarity = rarity;
	}

	public String getChestName() {
		return chestName;
	}
	
	public RarityEnum[] getRarity() {
		return rarity;
	}
	
	public static magicChestEnum getChest(String chestName) {
		for (magicChestEnum chest : magicChestEnum.values()) {
			if (chest.getChestName().equalsIgnoreCase(chestName)) {
				return chest;
			}
		}
		return null;
	}
	
}
