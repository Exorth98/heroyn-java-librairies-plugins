package net.heroyn.heroynserverapi.cosmetics.gadget.gadgets;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
import net.heroyn.heroynserverapi.listeners.CancelListener;
import net.heroyn.heroynserverapi.nms.other.NMS;
import net.heroyn.heroynserverapi.utils.Head;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;

@SuppressWarnings("unchecked")
public class GhostattackGadget extends IGadget
{
    List<Entity> entities;
    private int step;
    
    public GhostattackGadget(final Player player) {
        super(player, new UtilItem(288, (byte) 0, "&fGhostAttack").getItem(), 15);
        this.entities = new ArrayList<Entity>();
        this.step = 0;
    }
    
    @SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
    public void start() {
        final Location location = this.player.getLocation();
        new BukkitRunnable() {
            public void run() {
                GhostattackGadget.this.stop();
            }
        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 200L);
        for (int i = 0; i < 16; ++i) {

			final ArmorStand armorStand = (ArmorStand)this.player.getWorld().spawn(this.player.getLocation(), (Class)ArmorStand.class);
            NMS.setSilent((Entity)armorStand);
            armorStand.setSmall(true);
            armorStand.setVisible(false);
            armorStand.setLeggings(UtilItem.getColorArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
            armorStand.setBoots(UtilItem.getColorArmor(Material.LEATHER_BOOTS, Color.WHITE));
            armorStand.setHelmet(UtilItem.getSkull((UtilMath.randomRange(0, 1) > 0) ? Head.GHOST.getTexture() : Head.GHOST_REDEYE.getTexture()));
            armorStand.setChestplate(UtilItem.getColorArmor(Material.LEATHER_CHESTPLATE, Color.WHITE));
            armorStand.setItemInHand(new UtilItem(Material.STONE_HOE, (byte)0).getItem());
            this.entities.add((Entity)armorStand);
            CancelListener.getProtectedEntity().add((Entity)armorStand);
            final Bat silent = (Bat)location.getWorld().spawn(this.player.getLocation().add(0.0, 4.0, 0.0), (Class)Bat.class);
            silent.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));
            this.entities.add((Entity)silent);
            CancelListener.getProtectedEntity().add((Entity)silent);
            silent.setPassenger((Entity)armorStand);
            NMS.setSilent((Entity)silent);
        }
        this.player.getWorld().playSound(this.player.getLocation(), Sound.ENTITY_WOLF_HOWL, 1.0f, 2.0f);
    }
    
    @Override
    public void update() {
        super.update();
        if (this.entities.isEmpty()) {
            return;
        }
        if (this.step % 80 == 0) {
            this.player.getWorld().playSound(this.player.getLocation(), Sound.ENTITY_GUARDIAN_DEATH, 1.0f, 0.0f);
            this.player.getWorld().playSound(this.player.getLocation(), Sound.ENTITY_WOLF_HOWL, 1.0f, 0.0f);
        }

        this.entities.forEach(armorStand -> {
            if (armorStand instanceof Bat) {
                UtilParticle.sendParticle(((Entity)armorStand).getLocation().add(0.0, 0.5, 0.0), Particle.CLOUD, 1, new Vector(0.05, 0.05, 0.05), 0.1f);
            }
            else if (armorStand instanceof ArmorStand) {
                final ArmorStand armorStand2;
                armorStand2 = (ArmorStand) armorStand;
                if (this.step % 40 == 0) {
                    armorStand2.setHeadPose(new EulerAngle(Math.toRadians(UtilMath.randomRange(-80, 80)), Math.toRadians(UtilMath.randomRange(-80, 80)), Math.toRadians(UtilMath.randomRange(-80, 80))));
                }
            }
            return;
        });
        ++this.step;
    }
    
    @Override
    public void stop() {
        this.entities.forEach(entity -> {
            CancelListener.getProtectedEntity().remove(entity);
            entity.remove();
            return;
        });
        this.entities.clear();
    }
}
