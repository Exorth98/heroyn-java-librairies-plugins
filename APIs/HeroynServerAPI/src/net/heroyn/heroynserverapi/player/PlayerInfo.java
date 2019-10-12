package net.heroyn.heroynserverapi.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import net.heroyn.heroynserverapi.cosmetics.disguise.Morph;
import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
import net.heroyn.heroynserverapi.cosmetics.minion.Minion;
import net.heroyn.heroynserverapi.cosmetics.particle.Particles;
import net.heroyn.heroynserverapi.cosmetics.pet.Pet;
import net.heroyn.heroynserverapi.nms.entity.EntityFollow;

public class PlayerInfo {

	private static Map<UUID, PlayerInfo> playerInfos;
	private Player player;
	private float previousYaw;
	private int globalTime;
	private Morph morph;
	private Minion minion;
	private IGadget gadget;
	private Particles particle;
	private Pet pet;
	private List<EntityFollow> entityFollowList;
	private Location secondPlayerLocation;
	private boolean isMoving;
	private Location lastLocation;
	
	
	static {
		PlayerInfo.playerInfos = new HashMap<UUID, PlayerInfo>();
	}
	
	public PlayerInfo(final Player player) {
		this.entityFollowList = new ArrayList<EntityFollow>();
		this.isMoving = false;
		this.globalTime = 0;
		this.player = player;
	}
	
	/**
	 * Initialise le joueur
	 * ou le return
	 * @param player
	 * @return
	 */
	public static PlayerInfo instanceOf(final Player player) {
		PlayerInfo.playerInfos.putIfAbsent(player.getUniqueId(), new PlayerInfo(player));
		if (PlayerInfo.playerInfos.containsKey(player.getUniqueId())) {
			PlayerInfo.playerInfos.get(player.getUniqueId()).updatePlayer(player);
		}
		return PlayerInfo.playerInfos.get(player.getUniqueId());
	}
	
	/**
	 * Update le joueur
	 * @param player
	 */
	public void updatePlayer(final Player player) {
		this.player = player;
	}
	
	/**
	 * return le joueur
	 * @return
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * return la liste de tous les playerInfo
	 * @return
	 */
	public static Map<UUID, PlayerInfo> getPlayerInfos() {
		return playerInfos;
	}
	
	/**
	 * return si c'est le meme familliers
	 * @param minion
	 * @return
	 */
	public boolean isSameFamilliers(Class<?> minion) {
		return getMinion().getClass().equals(minion);
	}
	
	public void setSecondPlayerLocation(Location secondPlayerLocation) {
		this.secondPlayerLocation = secondPlayerLocation;
	}
	
	public Location getSecondPlayerLocation() {
		return secondPlayerLocation;
	}
	
	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}
	
	public Location getLastLocation() {
		return lastLocation;
	}
	
	/**
	 * return si le joueur bouge ou non
	 * @return
	 */
	public boolean isMoving() {
		return isMoving;
	}
	
	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}
	
	public int getGlobalTime() {
		return globalTime;
	}
	
	public void addTime() {
		++this.globalTime;
		if (this.globalTime >= 999999)
			this.globalTime = 0;
	}
	
	/**
	 * retir, la list des entity qui suit le joueur
	 * @return
	 */
	public List<EntityFollow> getEntityFollowList() {
		return entityFollowList;
	}
	
	public float getPreviousYaw() {
		return previousYaw;
	}
	
	public void setPreviousYaw(float previousYaw) {
		this.previousYaw = previousYaw;
	}
	
	/**
	 * return le deguisement du joueur
	 * @return
	 */
	public Morph getMorph() {
		return morph;
	}
	
	public void setMorph(Morph morph) {
		this.morph = morph;
	}

	public Minion getMinion() {
		return minion;
	}

	public void setMinion(Minion minion) {
		this.minion = minion;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}
	
	public Pet getPet() {
		return this.pet;
	}

	public IGadget getGadget() {
		return gadget;
	}

	public void setGadget(IGadget gadget) {
		this.gadget = gadget;
	}
	
	public void setParticle(Particles particle) {
		this.particle = particle;
	}

	public Particles getParticle() {
		return this.particle;
	}
	/////////////////////////////////////////////////
	List<String> permissions = new ArrayList<>();
	public double dreams;
	
	public void addDreams(double dreams) {
		this.dreams += dreams;
	}
	
	public void removeDreams(double dreams) {
		this.dreams -= dreams;
	}
	
	public double getDreams() {
		return this.dreams;
	}
	
	public void addPermissions(String permission) {
		permissions.add(permission);
	}
	
	public boolean hasPermissions(String permission) {
		for (String str : permissions) {
			if (str.equalsIgnoreCase(permission)) {
				return true;
			}
		}
		return false;
	}
	/////////////////////////////////////////////////
}
