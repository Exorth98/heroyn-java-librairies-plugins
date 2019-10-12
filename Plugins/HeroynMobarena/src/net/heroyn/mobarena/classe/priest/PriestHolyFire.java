package net.heroyn.mobarena.classe.priest;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.heroyn.heroynapi.Particles.Particles;
import net.heroyn.heroynapi.Target.TargetInfoEntity;
import net.heroyn.heroynapi.Target.TargetUtils;
import net.heroyn.heroynapi.utils.Flags;
import net.heroyn.heroynapi.utils.Flags.FlagsEnum;
import net.heroyn.heroynapi.utils.MathUtils;
import net.heroyn.heroynapi.utils.Task.TaskManager;
import net.heroyn.mobarena.HeroynMobarena;
import net.heroyn.mobarena.classe.Skill;
import net.heroyn.mobarena.utils.ClasseMsg;
import net.minecraft.server.v1_12_R1.EntityLiving;

public class PriestHolyFire extends Skill {

	public static String displayName = "§fSceptre du §6Dieu";
	
	public PriestHolyFire(int level, Player player) {
		super(level, player);
	}

	@Override
	protected void setCost() {
		this.cost = (30 - level);
	}

	@Override
	protected void setAttackPower() {
		this.attackPower = (80 + level * 5F) / 10;
	}

	@Override
	protected void setTargetLimit() {
		this.targetLimit = 15;
	}

	@Override
	protected void setCooldown() {
		this.cooldown = (100 - level * 4F) / 10;
	}

	@Override
	protected void setDescription() {
		this.name = displayName;
		this.description = new ArrayList<String>();
		description.add("§7Repousse et enflamme la cible");
		description.add("§7Dégâts: §b" + this.attackPower + " §7Points de dégâts");
		description.add("§7Coût: §b" + this.cost + " §7points d'énergie");
		description.add("§7Recharge: §b" + this.cooldown + " §7secondes");
	}

	@Override
	protected void setItemStack() {
		this.buildItem = new ItemStack(Material.TRIPWIRE_HOOK);
	}

	@Override
	public void use(Player player) {
		TargetInfoEntity target = TargetUtils.getTargetInfoEntity(player, targetLimit, 1.40, false, true);
		if (target == null) {
			ClasseMsg.invalidTarget(player);
			return;
		}
		if (!checkForCooldownAndEnergy(player, true, true))return;
			new SpellEffect(player, target.getEntity(), 0.5);
			playEffect(player, target);
	}
	
	public void playEffect(final Player caster, TargetInfoEntity target) {
		Vector line = target.getEntity().getLocation().toVector().subtract(caster.getLocation().toVector()).normalize();
		target.getEntity().setVelocity(line.clone().multiply(1.4).setY(0.5));
		((CraftLivingEntity) target.getEntity()).damage(0.1);
		// Rayon de feu
		caster.playSound(caster.getLocation(), Sound.BLOCK_LAVA_POP, 1F, 2F);
		Location startPoint = caster.getEyeLocation();
		for (int x = (int) target.getDistance(); x > 0; x--) {
			startPoint.add(line);
			for (Player pls : Bukkit.getOnlinePlayers()) {
				Particles.FLAME.display(0.2F, 0.2F, 0.2F, 0, 2, startPoint, pls);
			}
			spawnFire(target.getEntity());
		}
	}

	class SpellEffect {

		Entity player;
		EntityLiving entityL;
		Player caster;
		String taskName;
		int id;
		int tick = 0;
		double power;
		
		public SpellEffect(Player caster, Entity player, double power) {
			this.player = player;
			this.entityL = ((CraftLivingEntity) player).getHandle();
			this.caster = caster;
			this.taskName = "PriestHolyFire_" + player.getEntityId();
			this.power = power;
			run();
		}
		
		@SuppressWarnings("deprecation")
		public void run() {
			id = Bukkit.getScheduler().scheduleAsyncRepeatingTask(HeroynMobarena.getInstance(), new Runnable() {
				@Override
				public void run() {
					tick++;
					if (tick < 10 && player != null && player.isValid() && caster.isValid()) {
						player.setFireTicks(10);
						Flags.setStringFlag(player, FlagsEnum.LastDamager.getId(), caster.getName());
						double health = entityL.getHealth() - power;
						entityL.setHealth((float) (health > 0 ? health : 0));
						for (Player pls : Bukkit.getOnlinePlayers()) {
							Particles.LAVA.display(0.2F, 0.2F, 0.2F, 0 , 3, ((CraftLivingEntity) player).getEyeLocation(), pls);
						}
						player.getWorld().playSound(player.getLocation(), Sound.BLOCK_FIRE_AMBIENT, 0.5F, 2F);
					} 
					else {
						Bukkit.getScheduler().cancelTask(id);
					}
				}
			}, 0, 10);
		}
	}
	
	public void spawnFire(Entity entity) {
		for (int x = 0; x <= 5; x++) {
			ItemStack fire = new ItemStack(Material.FIRE);
			ItemMeta im = fire.getItemMeta();
			im.setDisplayName("Fire_Effect_"+x);
			fire.setItemMeta(im);
			final Item item = entity.getWorld().dropItemNaturally(((CraftLivingEntity) entity).getEyeLocation(), fire);
			Flags.setFlag(item, FlagsEnum.GODMODE.getId());
			item.setPickupDelay(20*5);
			item.setFireTicks(20*5);
			double dX = MathUtils.randomDouble(0.5, -0.5);
			double dY = 0.5;
			double dZ = MathUtils.randomDouble(0.5, -0.5);
			item.setVelocity(new Vector(dX, dY, dZ));
			TaskManager.runTaskLater(new Runnable() {
				@Override
				public void run() {
					if (item != null && item.isValid()) item.remove();
				}
			}, 20*2);
		}
	}
	
}
