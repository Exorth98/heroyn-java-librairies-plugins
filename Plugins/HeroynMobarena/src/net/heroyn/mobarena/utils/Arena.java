package net.heroyn.mobarena.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynapi.utils.ConfigUtils;
import net.heroyn.heroynapi.utils.Cuboid;
import net.heroyn.heroynapi.utils.Zone;
import net.heroyn.mobarena.HeroynMobarena;

public class Arena {

	private Plugin plugin = HeroynMobarena.getInstance();
	private HashMap<String,ConfigManager> CfgMngrs = HeroynMobarena.getInstance().ConfigManagers;
	
	private List<String> classes;
	
	private String name;
	private ArenaType type;
	private Zone zone;
	private boolean enabled;
	private List<Location> spawnPoints;
	private List<Location> lootPoints;
	private List<Location> bonusButtons;
	private List<ItemStack> loots;
	private List<ItemStack> bonusItems;
	private Location spectateLocation;
	private Location suitupLocation;
	private int maxPlayers;
	
	
	
	//CONSTRUCTEURS
	
	public Arena(String arenaSt) {
		
		this.name = arenaSt;
		this.type = getTypeFromCfg(arenaSt);
		this.zone = getZoneFromcfg(arenaSt);
		this.enabled = getEnableFromCfg(arenaSt);
		this.spawnPoints = getSpawnPointsFromCfg();
		this.lootPoints = getLootsPointsFromCfg();
		this.bonusButtons = getBonusButtonsFromCfg();
		this.loots = getLootItemsFromCfg();
		this.bonusItems = getBonusItemsFromCfg();
		this.spectateLocation = getSpectateLocationFromCfg();
		this.suitupLocation = getSuitupLocationFromCfg();
		this.classes = getClassesFromCfg();
		this.maxPlayers = getMaxPlayersFromCfg();
	}
	

	public Arena(String arenaSt, String type) {
		
		create(arenaSt, type);
		this.name = arenaSt;
		this.type = ArenaType.getType(type);
		this.zone = getZoneFromcfg(arenaSt);
		this.enabled = getEnableFromCfg(arenaSt);
		this.spawnPoints = getSpawnPointsFromCfg();
		this.lootPoints = getLootsPointsFromCfg();
		this.bonusButtons = getBonusButtonsFromCfg();
		this.loots = getLootItemsFromCfg();
		this.bonusItems = getBonusItemsFromCfg();
		this.spectateLocation = getSpectateLocationFromCfg();
		this.suitupLocation = getSuitupLocationFromCfg();
		this.classes = getClassesFromCfg();
		this.maxPlayers = getMaxPlayersFromCfg();
		
	}
	

	//-------------------MEHODES--------------------------------
	
	//GENERAL
	
	
	public void create(String name,String type) {
		
		List<String> arenas = getArenasNames();
		arenas.add(name);
		plugin.getConfig().set("Arenas", arenas);
		plugin.saveConfig();
		plugin.reloadConfig();
		
		HeroynMobarena.getInstance().loadArenaConfigs();
		
		CfgMngrs.get("arena." + name + ".Infos").getCustomConfig().set("Name",name);
		CfgMngrs.get("arena." + name + ".Infos").getCustomConfig().set("Type", ArenaType.getType(type).toString());
		
	}
	
	public void save() {	
		
		HeroynMobarena.getInstance().saveCustomConfigs();
		
	}		
	public void delete() {
		
		//A DEVELOPPER (Suppression dossier)
		
		List<String> arenas = getArenasNames();
		arenas.remove(this.name);
		plugin.getConfig().set("Arenas", arenas);
		plugin.saveConfig();
		plugin.reloadConfig();
	}
	public String display() {
		
		return "§a A venir";
	}

	public boolean isInGame() {		
		return HeroynMobarena.getInstance().games.containsKey(this.name);
	}

	
	//GETTERS
	
	public List<Player> getPlayersInsideZone() {
		
		return zone.getPlayers();
	}
	
	
	//FROM CONFIG
	private Zone getZoneFromcfg(String arenaSt) {
		
		ArenaType at;
		
		if(this.type == null) {
			at = getTypeFromCfg(arenaSt);
		}
		else at = this.type;
		
		if(at == ArenaType.CUBOID){
			String pos1 = CfgMngrs.get("arena." + arenaSt + ".Infos").getCustomConfig().getString("Pos1");
			String pos2 = CfgMngrs.get("arena." + arenaSt + ".Infos").getCustomConfig().getString("Pos2");
			
			Location pos1Loc = ConfigUtils.deserializeLocation(pos1);
			Location pos2Loc = ConfigUtils.deserializeLocation(pos2);
			
			Cuboid cubZone = new Cuboid(pos1Loc,pos2Loc);
			
			return (Zone) cubZone;
			
		}
		else if(at == ArenaType.CYL) {
			
			//TODO
			
		}
		
		return null;
		
	}
	private ArenaType getTypeFromCfg(String arenaSt) {
		
		String type = CfgMngrs.get("arena." + arenaSt + ".Infos").getCustomConfig().getString("Type");
		ArenaType at = ArenaType.getType(type);
		return at;
	}
	private boolean getEnableFromCfg(String arenaSt) {
		
		return CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().getBoolean("Enable");
		
	}	
	public List<Location> getSpawnPointsFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<String> points = (List<String>) CfgMngrs.get("arena." + this.name + ".SpawnPoints").getCustomConfig().get("Points");
		if(points == null) points = new ArrayList<String>();
		
		return ConfigUtils.deserializeLocationList(points);//																														//LIST
	}	
	public List<Location> getLootsPointsFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<String> points = (List<String>) CfgMngrs.get("arena." + this.name + ".LootPoints").getCustomConfig().get("Points");
		if(points == null) points = new ArrayList<String>();
		
		return ConfigUtils.deserializeLocationList(points);//																														//LIST
	}	
	public List<ItemStack> getLootItemsFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<ItemStack> items = (List<ItemStack>) CfgMngrs.get("arena." + this.name + ".Loots").getCustomConfig().get("Loots");
		if(items == null) items = new ArrayList<ItemStack>();
		
		return items;
		
	}	
	public List<ItemStack> getBonusItemsFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<ItemStack> items = (List<ItemStack>) CfgMngrs.get("arena." + this.name + ".BonusItems").getCustomConfig().get("Items");
		if(items == null) items = new ArrayList<ItemStack>();
		
		return items;
	}
	public List<Location> getBonusButtonsFromCfg(){
		
		@SuppressWarnings("unchecked")
		List<String> points = (List<String>) CfgMngrs.get("arena." + this.name + ".BonusButtons").getCustomConfig().get("Points");
		if(points == null) points = new ArrayList<String>();
		
		return ConfigUtils.deserializeLocationList(points);//																														//LIST
	}
	public Location getSpectateLocationFromCfg() {
		
		String sLoc = CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().getString("SpectateLocation");
		return ConfigUtils.deserializeLocation(sLoc);
	}
	public Location getSuitupLocationFromCfg() {
		
		String sLoc = CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().getString("SuitupLocation");
		return ConfigUtils.deserializeLocation(sLoc);
	}
	public List<String> getClassesFromCfg(){
		
		@SuppressWarnings("unchecked")
		ArrayList<String> classes = (ArrayList<String>) CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().get("Classes");
		if(classes == null){classes = new ArrayList<String>();}
		
		return classes;
		
	}
	private int getMaxPlayersFromCfg() {
		
		int max = CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().getInt("MaxPlayers");
		
		return (max != 0) ? max : 10;
	}
	
	//FROM INSTANCE	
	public boolean isEnabled() {
		return this.enabled;		
	}	
	public Zone getZone() {		
		return this.zone;		
	}
	public ArenaType getType() {		
		return this.type;
	}	
	public String getName() {
		return this.name;
	}
	public List<Location> getSpawnPoints(){
		return this.spawnPoints;
	}
	public List<Location> getLootPoints(){
		return this.lootPoints;
	}
	public List<Location> getBonusButtons(){
		return this.bonusButtons;
	}
	public List<ItemStack> getLoots(){
		return this.loots;
	}
	public List<ItemStack> getBonusItems(){
		return this.bonusItems;
	}
	public Location getSpectacteLocation() {
		return this.spectateLocation;
	}
	public Location getSuitupLocation() {
		return this.suitupLocation;
	}
	public List<String> getClasses(){
		return this.classes;
	}
	public int getMaxPlayers() {
		return this.maxPlayers;
	}
	
	//SETTERS
	
	public void addSpawnPoint(Location loc) {	

		List<Location> points = getSpawnPointsFromCfg();
		List<String> sPoints = ConfigUtils.serializeLocationList(points);													//LIST
		String sLoc = ConfigUtils.serializeLocation(loc);
		points.add(loc);
		sPoints.add(sLoc);	
		CfgMngrs.get("arena." + this.name + ".SpawnPoints").getCustomConfig().set("Points", sPoints);
		this.spawnPoints = points;
		this.save();
	}	
	public void setCuboidPos1(Location pos1) {
		
		if(this.type == ArenaType.CUBOID) {
			
			String sLoc = ConfigUtils.serializeLocation(pos1);
			CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().set("Pos1", sLoc);
			this.save();
			this.zone = getZoneFromcfg(name);
			
		}
	}
	public void setCuboidPos2(Location pos2) {
		
		if(this.type == ArenaType.CUBOID) {
			
			String sLoc = ConfigUtils.serializeLocation(pos2);
			CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().set("Pos2", sLoc);
			this.save();
			this.zone = getZoneFromcfg(name);
			
		}
		
	}
	public void setSpectateLocation(Location spectateLoc) {
			
		String sLoc = ConfigUtils.serializeLocation(spectateLoc);
		CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().set("SpectateLocation", sLoc);
		this.spectateLocation = spectateLoc;
		this.save();	
	}
	public void addLootLocation(Location spawnPointLoc) {
		
		List<Location> points = getLootsPointsFromCfg();
		List<String> sPoints = ConfigUtils.serializeLocationList(points);													//LIST
		String sLoc = ConfigUtils.serializeLocation(spawnPointLoc);
		points.add(spawnPointLoc);
		sPoints.add(sLoc);		
		CfgMngrs.get("arena." + this.name + ".LootPoints").getCustomConfig().set("Points", sPoints);
		this.lootPoints = points;
		this.save();
		
		
	}

	public void addBonusItem(ItemStack item) {
		
		List<ItemStack> items = getBonusItemsFromCfg();	
		items.add(item);
		
		CfgMngrs.get("arena." + this.name + ".BonusItems").getCustomConfig().set("Items", items);
		this.bonusItems = items;
		this.save();
		
	}

	public void addLootItem(ItemStack item) {
		
		List<ItemStack> items = getLootItemsFromCfg();		
		items.add(item);
		
		CfgMngrs.get("arena." + this.name + ".Loots").getCustomConfig().set("Loots", items);
		this.loots = items;
		this.save();
		
	}
	
	public void setEnable(boolean b) {
		
		this.enabled = b;
		CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().set("Enable", b);
		this.save();
		
	}

	public void addBonusButton(Location loc) {
		
		List<Location> points = getBonusButtonsFromCfg();
		List<String> sPoints = ConfigUtils.serializeLocationList(points);													//LIST
		String sLoc = ConfigUtils.serializeLocation(loc);
		points.add(loc);
		sPoints.add(sLoc);
		CfgMngrs.get("arena." + this.name + ".BonusButtons").getCustomConfig().set("Points",sPoints);		
		this.bonusButtons = points;																							 
		this.save();
	}
	
	public void setSuitupLocation(Location loc) {
		
		String sLoc = ConfigUtils.serializeLocation(loc);
		CfgMngrs.get("arena." + this.name + ".Infos").getCustomConfig().set("SuitupLocation", sLoc);
		this.suitupLocation = loc;
		this.save();
	}
	
	
	
	//---------------METHODES STATIC----------------------------------
	
	public static boolean exists(String arenaSt) {
		
		return getArenasNames().contains(arenaSt);
	}
	public static List<String> getArenasNames() {

		@SuppressWarnings("unchecked")
		List<String> arenas = (List<String>) HeroynMobarena.getInstance().getConfig().get("Arenas");
		if(arenas == null) {arenas = new ArrayList<String>();}
		
		return arenas;
	}
	public static List<Arena> getArenas() {

		List<Arena> arenas = new ArrayList<>();
		
		for(String ar : getArenasNames()) {
			arenas.add(new Arena(ar));
		}
		
		return arenas;
	}
	
	public static Arena getArena(String arenaSt, Player p) {
		
		if( !arenaSt.equals("X")) {
			
			if(exists(arenaSt))return new Arena(arenaSt);
			return null;
			
		}else {
			
			for(String ar : getArenasNames()){
				
				Arena arena = new Arena(ar);
				if( arena.getPlayersInsideZone().contains(p))return arena;		
				
			}			
		}		
		return null;
	}









}
