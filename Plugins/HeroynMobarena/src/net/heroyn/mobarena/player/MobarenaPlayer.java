package net.heroyn.mobarena.player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;

import net.heroyn.mobarena.classe.ClasseBase;
import net.heroyn.mobarena.utils.Arena;

public class MobarenaPlayer {
	
	public static Map<UUID, MobarenaPlayer> mobarenaplayer;
	
	public Player player;
	public Arena arena;	
	public double score;
	public ClasseBase classe;
	
	static {
		mobarenaplayer = new HashMap<>();
	}
	
	public MobarenaPlayer(final Player player) {
		this.player = player;
	}
	
	/**
	 * Initialise le joueur
	 * ou le return
	 * @param player
	 * @return
	 */
	public static MobarenaPlayer instanceOf(final Player player) {
		MobarenaPlayer.mobarenaplayer.putIfAbsent(player.getUniqueId(), new MobarenaPlayer(player));
		if (MobarenaPlayer.mobarenaplayer.containsKey(player.getUniqueId())) {
			MobarenaPlayer.mobarenaplayer.get(player.getUniqueId()).updatePlayer(player);
		}
		return MobarenaPlayer.mobarenaplayer.get(player.getUniqueId());
	}
	
	/**
	 * Met la classe au joueur
	 * @param classe
	 */
	public void setClasse(ClasseBase classe) {
		this.classe = classe;
	}
	
	/**
	 * return la classe du joueur
	 * @return classe
	 */
	public ClasseBase getClasse()
	{return this.classe;}
	
	
	/**
	 * Update le joueur
	 * @param player
	 */
	public void updatePlayer(Player player)
	{
		this.player = player;
	}
	
	/**
	 * return le joueur
	 * @return
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * return l'arene
	 * @return
	 */
	public Arena getArena() {
		return this.arena;
	}
	
	/**
	 * return le score 
	 * @return
	 */
	public double getScore() {
		return this.score;
	}
	
}
