package fr.exorth.pickaxes;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.exorth.MineGestion;

public class RentPickaxe {
	
	static FileConfiguration storage = MineGestion.getInstance().cfgmRS.getCustomConfig();
	
	@SuppressWarnings("unchecked")
	static ArrayList<String> refs = (ArrayList<String>) storage.get("InRentRefs");
	
	String ref;
	UUID owner;
	long expiration;
	PickaxeType type;
	
	public RentPickaxe(String ref, PickaxeType type, Player p, long expiration) {
		
		this.ref=ref;
		this.type=type;
		this.owner=p.getUniqueId();
		this.expiration=expiration;
		
	}
	
	public RentPickaxe(String ref, PickaxeType type, UUID owner, long expiration) {
		
		this.ref=ref;
		this.type=type;
		this.owner=owner;
		this.expiration=expiration;
		
	}
	
	private static RentPickaxe getPickaxe(String ref) {
		
		PickaxeType type = PickaxeType.valueOf((String)storage.get(ref + ".type"));
		UUID owner = UUID.fromString((String)storage.get(ref + ".owner"));
		String expirationS = (String) storage.get(ref + ".expiration");
		long expiration = Long.parseLong(expirationS);
		
		RentPickaxe rentP = new RentPickaxe(ref,type,owner,expiration);
		
		return rentP;
	}
	
	public static boolean hasPickaxe(UUID owner) {
		
		boolean rep=false;
		
		if(refs==null){refs= new ArrayList<String>();}
		
		for(String ref : refs){
			UUID uuid = UUID.fromString((String)storage.get(ref + ".owner"));
			if(uuid.equals(owner)){
				rep = true;
			}
		}
		
		return rep;
		
	}
	
	public static RentPickaxe getPickaxe(UUID owner) {
		
		if(refs==null){refs= new ArrayList<String>();}
		String ref="";
		
		for(String ref2 : refs){
			UUID uuid = UUID.fromString((String)storage.get(ref2 + ".owner"));
			if(uuid.equals(owner)){
				ref=ref2;
			}
		}
		
		RentPickaxe rentP = getPickaxe(ref);
		
		return rentP;
	}
	
	public void deletePickaxe(String ref) {
		
		if(refs==null){refs= new ArrayList<String>();}
		refs.remove(ref);
		storage.set("InRentRefs", refs);
		storage.set(ref + ".expiration", null);
		storage.set(ref + ".type", null);
		storage.set(ref + ".owner", null);
		storage.set(ref, null);
		
		MineGestion.getInstance().cfgmRS.saveCustomConfig();
		
	}
	
	public void SavePickaxe() {
		
		if(refs==null){refs= new ArrayList<String>();}
		
		String ref = this.ref;
		
		refs.add(ref);
		storage.set("InRentRefs", refs);
		storage.set(ref + ".expiration", Long.toString(expiration));
		storage.set(ref + ".type", type.toString());
		storage.set(ref + ".owner", owner.toString());
		
		MineGestion.getInstance().cfgmRS.saveCustomConfig();
		
	}
	
	
	public void setExpiration(long expiration) {
		
		
		
		this.expiration=expiration;
		
		storage.set(this.ref + ".expiration", Long.toString(expiration));
		
		MineGestion.getInstance().cfgmRS.saveCustomConfig();
		
	}
	
	
	public void setType(PickaxeType type) {
		
		this.type=type;
		
		storage.set(this.ref + ".type", type.toString());
		
		MineGestion.getInstance().cfgmRS.saveCustomConfig();
	}
	
	public void setOwnerUUID(UUID owner) {
		
		this.owner=owner;
		
		storage.set(this.ref + ".owner", owner.toString());
		
		MineGestion.getInstance().cfgmRS.saveCustomConfig();
	}
	
	public PickaxeType getType() {
		
		return this.type;
		
	}
	
	public long getExpiration() {
		
		return this.expiration;
		
	}
	
	public UUID getOwnerUUID() {
		
		return this.owner;
		
	}
	
	public String getRef() {
		
		return this.ref;
		
	}


}
