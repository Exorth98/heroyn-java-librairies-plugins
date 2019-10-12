package net.heroyn.mobarena.game;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;

import io.netty.util.internal.ThreadLocalRandom;
import net.heroyn.heroynapi.utils.ScoreboardSign;
import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.game.player.InMobarenaPlayer;
import net.heroyn.mobarena.game.player.MobarenaFighter;
import net.heroyn.mobarena.game.player.MobarenaSpectator;
import net.heroyn.mobarena.game.player.MobarenaSuitingupPlayer;
import net.heroyn.mobarena.game.taskrunnables.LootTaskRunnable;
import net.heroyn.mobarena.game.taskrunnables.MobSpawnTaskRunnable;
import net.heroyn.mobarena.game.taskrunnables.ScorebordRefreshRunnable;
import net.heroyn.mobarena.game.taskrunnables.TimerTaskRunnable;
import net.heroyn.mobarena.utils.Arena;
import net.heroyn.mobarena.utils.HeroynMobarenaUtils;

public class MobarenaGame {
	
	
	//GENERAL
	Plugin plugin = HeroynMobarena.getInstance();
	NumberFormat formatter = new DecimalFormat("#0.00");
	
	//PLAYERS
	private List<Player> Bannedplayers = new ArrayList<>();
	private List<MobarenaFighter> ButtonBannedplayers = new ArrayList<>();	
	private List<InMobarenaPlayer> players = new ArrayList<>();
	
	//SCOREBOARDS
	private Map<Player, ScoreboardSign> sbs = new HashMap<>();
	
	//DATASAVING
	public static  HashMap<Player, MobarenaDataCard> playersDatas = new HashMap<>();
	
	//TASK
	private int mobSpawnTask;
	final int mobSpawnTime = 30;
	
	private int lootTask;
	final int lootsTime = 30;
	
	private int timerTask;
	
	private int sbRefreshTask;
	
	//TIMER / Wave
	int gameTimer = 0;
	int wave = 0;
	
	
	
	//========================
	private Arena arena;
	private double globalScore;
	private double globalCoins;
	private MobarenaGameState state;
	//========================
	
	
	public MobarenaGame(Arena arena) {
		
		this.arena = arena;
		this.globalScore = 0;
		if(arena.isInGame())this.state = MobarenaGameState.FIGHTING;
		else {
			this.state = MobarenaGameState.WAITING;
			HeroynMobarena.getInstance().games.put(arena.getName(),this);
		}
		
	}
	
	/**
	 * Lancer le combat
	 */
	@SuppressWarnings("deprecation")
	public void Run() {
		
		Bukkit.broadcastMessage("§bL'arène mobarena §a"+this.arena.getName()+" §bvient de démarrer !");
		setState(MobarenaGameState.FIGHTING);
		
		//RUN TASKS
		lootTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new LootTaskRunnable(this), 200, 20*lootsTime);
		mobSpawnTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new MobSpawnTaskRunnable(this), 100, 20*mobSpawnTime);
		timerTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new TimerTaskRunnable(this), 0, 20);
		sbRefreshTask = Bukkit.getScheduler().scheduleAsyncRepeatingTask(plugin, new ScorebordRefreshRunnable(this), 0 , 5);
	}
	
	/**
	 * Arrêter l'arène
	 */
	public void end() {
		
		//EJECT SPECTATOR AND SUITINGUP
		for(MobarenaSpectator maS: this.getSpectators()) {			
			leaveSpectator(maS);
		}
		for(MobarenaSuitingupPlayer maSupP: getSuitingupPlayers()) {			
			leaveSuitingup(maSupP);
		}
		
		//CANCEL TASKS
		Bukkit.getScheduler().cancelTask(lootTask);
		Bukkit.getScheduler().cancelTask(mobSpawnTask);
		Bukkit.getScheduler().cancelTask(timerTask);
		Bukkit.getScheduler().cancelTask(sbRefreshTask);

		//REMOVE ITEMS ET MOBS
		for(Entity e : this.arena.getZone().getWorld(true).getEntities()) {			
			if(this.arena.getZone().isInZone(e.getLocation())) {				
				if(e instanceof Item || e instanceof Monster || e instanceof Arrow)e.remove();
			}
		}
		
		//END
		Bukkit.broadcastMessage("§bL'arène mobarena §a"+this.arena.getName()+" §bvient de finir ! Vous pouvez revenir jouer !");
		HeroynMobarena.getInstance().games.remove(this.arena.getName());
		
	}
	
	public void setSbLines(ScoreboardSign sb) {
		sb.setLine(9,	"Joueurs : " + this.getInGamePlayerNumber());
		sb.setLine(8,	"Spectateurs : "+ this.getSpectators().size());
		sb.setLine(7,	"§0                              §0");
		sb.setLine(6,	"Score total : " + formatter.format(this.globalScore));
		sb.setLine(5,	"Coins total : " + formatter.format(this.globalCoins));
		sb.setLine(4,	"§2                             §2");
		sb.setLine(3,	"Timer : "+ HeroynMobarenaUtils.getTimer(this.gameTimer));
		sb.setLine(2,	"Vague : "+ this.wave);
		sb.setLine(1,	"§1                           §1");	
	}
	
	public ScoreboardSign getScoreboard(Player p) {
		ScoreboardSign sb = new ScoreboardSign(p, "§6Mobarena");
		setSbLines(sb);
		return sb;
	}
	
	public void addScoreboard(Player p) {		
		ScoreboardSign sb = getScoreboard(p);
		sb.create();
		this.sbs.put(p, sb);
	}
	
	public void removeScoreboard(Player p) {		
		ScoreboardSign sb = this.sbs.get(p);
		sb.destroy();
		this.sbs.remove(p);
		
	}
	
	public void updateScoreboard(Player p) {
		
		ScoreboardSign sb = this.sbs.get(p);
		setSbLines(sb);
	}
	
	public void timerAdd() {
		
		this.gameTimer++;
	}
	public int getTimer() {
		
		return this.gameTimer;
	}
	
	public void addWave() {
		this.wave++;
	}
	
	public void DieFighter(MobarenaFighter maF) {
		
		FinishFighter(maF);
	}
	
	public void FinishFighter(MobarenaFighter maF) {
		
		leaveFighter(maF);
		
		//COMPTABILISATION OF SCORE FOR REWARDS
		
	}
	
	public void leaveFighter(MobarenaFighter maF) {
		
		removeFighter(maF);
		addBannedPlayer(maF.getPlayer());
		restoreDatas(maF.getPlayer());
		
		removeScoreboard(maF.getPlayer());
	}
	public void leaveSpectator(MobarenaSpectator maS) {
		
		this.removeSpectator(maS);
		//restoreDatas(maS.getPlayer());
		maS.getPlayer().setGameMode(GameMode.SURVIVAL);
		
		removeScoreboard(maS.getPlayer());

	}



	public void leaveSuitingup(MobarenaSuitingupPlayer maSupP) {
		
		this.removeSuitingupPlayer(maSupP);
		restoreDatas(maSupP.getPlayer());
		
	}

	public void playerJoinArena(MobarenaFighter maF) {
		

		addFighter(maF);
		maF.suitUp();
		maF.getPlayer().teleport(getRandomSpawnPoint());
		
		if(this.state == MobarenaGameState.WAITING) {
			
			this.Run();
		}
		//Bukkit.broadcastMessage("joueurs :" + this.players.size());
		
		addScoreboard(maF.getPlayer());
		
	}
	
	public void startPlayer(MobarenaSuitingupPlayer maSupP) {
		
		removeSuitingupPlayer(maSupP);
		MobarenaFighter maF = new MobarenaFighter(maSupP.getPlayer(), this.arena , maSupP.getSelectionnedCharacter());
		playerJoinArena(maF);
	}
	
	public void playerSpectate(MobarenaSpectator maS) {
		
		
		
		Player p = maS.getPlayer();
		saveAndResetDatas(p);

		p.teleport(this.arena.getSpectacteLocation());
		maS.getPlayer().setGameMode(GameMode.SPECTATOR);
		addSpectator(maS);
		
		removeScoreboard(maS.getPlayer());
		
	}

	public Location getRandomSpawnPoint() {
		
		List<Location> locs = this.arena.getSpawnPoints();		
		int i = ThreadLocalRandom.current().nextInt(0, locs.size());
		
		return locs.get(i);
	}
	public Location getRandomLootPoint() {
		
		List<Location> locs = this.arena.getLootPoints();		
		int i = ThreadLocalRandom.current().nextInt(0, locs.size());
		
		return locs.get(i);
	}
	public ItemStack getRandomLoot() {
		
		List<ItemStack> loots = this.arena.getLoots();		
		int i = ThreadLocalRandom.current().nextInt(0, loots.size());
		
		return loots.get(i);
	}


	public void playerJoinSuitup(Player p) {
		
		addSuitingupPlayer(new MobarenaSuitingupPlayer(p, arena));
		p.sendMessage("§aEquipe toi ! Puis fais §b'/ma start'§a pour rejondre le combat");
		saveAndResetDatas(p);
		
		p.teleport(this.arena.getSuitupLocation());
		

		
	}
	
	private void saveAndResetDatas(Player p) {
		
		MobarenaDataCard pDatas = new MobarenaDataCard(p);
		playersDatas.put(p, pDatas);
		p.getInventory().clear();
		p.setExp(0);
		p.setLevel(0);
		p.setFoodLevel(20);
		p.setHealth(20);
	    for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		
	}

	@SuppressWarnings("deprecation")
	private void restoreDatas(Player p) {
		
	    for (PotionEffect effect : p.getActivePotionEffects())
	        p.removePotionEffect(effect.getType());
		
		MobarenaDataCard pDatas = playersDatas.get(p);
		pDatas.restore();
		playersDatas.remove(p);
		p.resetMaxHealth();
		
	}
	
	public boolean isFull() {
		
		return this.getFighters().size() == this.arena.getMaxPlayers();
	}
	
	public int getInGamePlayerNumber() {
		
		return getFighters().size();
	}
	
	public void setGlobalScore(double gScore) {
		
		this.globalScore = gScore;
	}
	
	public void setGlobalCoins(double gCoins) {
		
		this.globalCoins = gCoins;
	}
	
	public double getGlobalCoins() {
		
		return this.globalCoins;
	}
	
	public void addCoins(double coins) {
		
		this.globalCoins += coins;
	}
	
	public void addScore(int score) {
		
		this.globalScore+= score;
	}
	
	public void addBannedPlayer(Player p) {
		
		this.Bannedplayers.add(p);
	}
	
	public void addButtonBannedPlayer(MobarenaFighter maP) {
		
		this.ButtonBannedplayers.add(maP);
	}
	
	public void addFighter(MobarenaFighter maF) {
		
		this.players.add(maF);
	}
	public void addSpectator(MobarenaSpectator maS) {
		
		this.players.add(maS);
	}
	public void addSuitingupPlayer(MobarenaSuitingupPlayer maSupP) {
		
		this.players.add(maSupP);
	}	
	public void removeFighter(MobarenaFighter maF) {
		
		this.players.remove(maF);
	}
	private void removeSpectator(MobarenaSpectator maS) {
		
		this.players.remove(maS);		
	}
	private void removeSuitingupPlayer(MobarenaSuitingupPlayer maSupP) {
		
		this.players.remove(maSupP);
		
	}
	public void setState(MobarenaGameState state) {
		
		this.state = state;
	}
	
	public Map<Player, ScoreboardSign> getScorboards(){
		
		return this.sbs;
	}
	
	public Arena getArena() {
		return this.arena;
	}
	
	public int getWave() {
		return this.wave;
	}
	
	public List<InMobarenaPlayer> getAllPlayers(){
		
		return this.players;
	}

	public List<MobarenaFighter> getFighters(){
		
		List<MobarenaFighter> fighters = new ArrayList<>();
		for( InMobarenaPlayer inMaP : this.players) {
			if(inMaP instanceof MobarenaFighter)
				fighters.add((MobarenaFighter) inMaP );
		}
		return fighters;
	}
	public List<Player> getBannedPlayers(){
		return this.Bannedplayers;
	}
	public List<MobarenaFighter> getButtonBannedPlayer() {		
		return this.ButtonBannedplayers;
	}
	public List<MobarenaSpectator> getSpectators(){
		
		List<MobarenaSpectator> spectators = new ArrayList<>();
		for( InMobarenaPlayer inMaP : this.players) {
			if(inMaP instanceof MobarenaSpectator)
				spectators.add((MobarenaSpectator) inMaP );
		}
		return spectators;
	}
	public double getGlobalScore() {
		return this.globalScore;
	}
	public MobarenaGameState getState() {
		return this.state;
	}
	public List<MobarenaSuitingupPlayer> getSuitingupPlayers(){
		
		List<MobarenaSuitingupPlayer> suitingup = new ArrayList<>();
		for( InMobarenaPlayer inMaP : this.players) {
			if(inMaP instanceof MobarenaSuitingupPlayer)
				suitingup.add((MobarenaSuitingupPlayer) inMaP );
		}
		return suitingup;
	}


























	

	
	

}
