package net.heroyn.heroynserverapi.magicchest;

import java.util.ArrayList;
import java.util.List;

public enum CosmeticsEnum {
	//////////////// GADGET //////////////// 
	GADGET_BATBLASTER("§6Gadget: §eBat Blaster", "Gadget_Batblaster", RarityEnum.COMMUN),
	GADGET_METEOR("§6Gadget: §eMeteor", "Gadget_Meteor", RarityEnum.EPIQUE),
	/////////////// PARTICLE //////////////
	PARTICLE_AUREOL("§6Particle: §eCouronne de Flamme", "Particle_Aureole", RarityEnum.RARE),
	PARTICLE_WINGS("§6Particle: §eAiles", "Particle_Wings", RarityEnum.LEGENDAIRE),
	PARTICLE_APOCALYPTIC("§6Particle: §eApocalypse", "Particle_Apocalypse", RarityEnum.HALLOWEEN);
	
	public String name;
	public String permissions;
	RarityEnum rarity;
	
	private CosmeticsEnum(String name, String permissions, RarityEnum rarity) {
		this.name = name;
		this.permissions = permissions;
		this.rarity = rarity;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPermissions() {
		return permissions;
	}
	
	public RarityEnum getRarity() {
		return rarity;
	}

	public static List<CosmeticsEnum> getCosmeticsWithRarity(RarityEnum... rarity) {
		List<CosmeticsEnum> list = new ArrayList<>();
		for (RarityEnum rar : rarity) {
			for (CosmeticsEnum cosmetics : CosmeticsEnum.values()) {
				if (cosmetics.getRarity().equals(rar)) {
					list.add(cosmetics);
				}
			}
		}
		return list;
	}
	
}
