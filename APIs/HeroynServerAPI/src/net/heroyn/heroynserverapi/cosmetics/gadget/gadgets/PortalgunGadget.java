package net.heroyn.heroynserverapi.cosmetics.gadget.gadgets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.HeroynServerAPI;
import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
import net.heroyn.heroynserverapi.nms.entity.ReflectedArmorStand;
import net.heroyn.heroynserverapi.utils.CC;
import net.heroyn.heroynserverapi.utils.UtilItem;
import net.heroyn.heroynserverapi.utils.UtilMath;
import net.heroyn.heroynserverapi.utils.UtilParticle;
import net.heroyn.heroynserverapi.utils.particle.ColoredParticle;

public class PortalgunGadget extends IGadget
{
    private Portal portal1;
    private Portal portal2;
    private boolean started;
    private BukkitTask task;
    
    public PortalgunGadget(final Player player) {
        super(player, new UtilItem(404, (byte) 0, "&3Portalgun").getItem(), 50);
        this.started = false;
    }
    
    @Override
    public void tryStartCooldownGadget() {
        if (!this.started) {
            if (this.portal1 == null) {
                super.tryStartCooldownGadget();
            }
        }
        else if (this.portal1 != null && this.portal2 != null) {
            this.portal1.remove();
            this.portal2.remove();
            this.portal2 = null;
            this.portal1 = null;
            this.player.playSound(this.player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0f, 0.0f);
        }
        else {
            this.start();
        }
    }
    
    @Override
    public void start() {
        this.started = true;
        if (this.portal1 == null) {
            this.portal1 = new Portal(this.player, Color.FUCHSIA);
        }
        else if (this.portal1 != null && this.portal2 == null) {
            if (this.portal1.getPortalLocation().distance(this.player.getLocation()) < 10.0) {
                this.player.sendMessage(CC.colored("§cVous devez poser votre portail plus loin !"));
            }
            else if (this.player.getWorld().equals(this.portal1.getPortalLocation().getWorld())) {
                this.portal2 = new Portal(this.player, Color.fromRGB(0, 255, 0));
            }
        }
        if (this.task == null) {
            this.task = new BukkitRunnable() {
                public void run() {
                    PortalgunGadget.this.stop();
                }
            }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 600L);
        }
    }
    
    @Override
    public void update() {
        super.update();
        if (this.portal1 != null) {
            this.portal1.update();
        }
        if (this.portal2 != null) {
            this.portal2.update();
        }
        if (this.portal2 != null && this.portal1 != null) {
            if (this.player.getLocation().distance(this.portal1.getPortalLocation()) <= 1.5 && !this.portal1.deny) {
                this.player.playSound(this.player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.0f);
                this.portal1.setDeny();
                this.portal2.setDeny();
                this.player.teleport(this.portal2.getPortalLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                this.player.setVelocity(this.portal2.getPortalLocation().getDirection().multiply(0.2).setY(0.3));
                this.portal1.circlePoints.forEach(location -> UtilParticle.sendParticle(location, Particle.CLOUD, 0, this.portal1.getPortalLocation().toVector().subtract(location.toVector()).multiply(2.5), 0.1f));
            }
            if (this.player.getLocation().distance(this.portal2.getPortalLocation()) <= 1.5 && !this.portal2.deny) {
                this.player.playSound(this.player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1.0f, 0.0f);
                this.portal1.setDeny();
                this.portal2.setDeny();
                this.player.teleport(this.portal1.getPortalLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN);
                this.player.setVelocity(this.portal1.getPortalLocation().getDirection().multiply(0.2).setY(0.3));
                this.portal2.circlePoints.forEach(location2 -> UtilParticle.sendParticle(location2, Particle.CLOUD, 0, this.portal2.getPortalLocation().toVector().subtract(location2.toVector()).multiply(2.5), 0.1f));
            }
        }
    }
    
    @Override
    public void stop() {
        if (this.portal1 != null) {
            this.portal1.remove();
        }
        if (this.portal2 != null) {
            this.portal2.remove();
        }
        this.portal1 = null;
        this.portal2 = null;
        this.started = false;
        this.task = null;
    }
    
    private class Portal
    {
        private double radius;
        double amount;
        private boolean deny;
        private Map<ReflectedArmorStand, Integer> portalblocks;
        private List<Location> circlePoints;
        private Location portalLocation;
        @SuppressWarnings("unused")
		private Player p;
        private Color c;
        
        @SuppressWarnings("deprecation")
		public Portal(final Player p3, final Color c) {
            this.radius = 1.2;
            this.amount = this.radius * 35.0;
            this.portalblocks = new HashMap<ReflectedArmorStand, Integer>();
            this.circlePoints = new ArrayList<Location>();
            this.c = c;
            this.p = p3;
            PortalgunGadget.this.player.playSound(PortalgunGadget.this.player.getLocation(), Sound.ENTITY_ZOMBIE_VILLAGER_CONVERTED, 1.0f, 2.0f);
            final Location add = p3.getLocation().add(0.0, 1.0, 0.0);
            add.setPitch(0.0f);
            this.portalLocation = add.toVector().add(add.getDirection().multiply(3)).toLocation(add.getWorld());
            final Location clone = this.portalLocation.clone();
            this.portalLocation.setDirection(add.getDirection().multiply(-0.1).normalize());
            final double n = 6.283185307179586 / this.amount;
            for (int n2 = 0; n2 < this.amount; ++n2) {
                final double n3 = n2 * n;
                final Vector multiply = PortalgunGadget.this.player.getLocation().getDirection().clone().multiply(Math.cos(n3) * this.radius);
                multiply.setY(Math.sin(n3) * this.radius);
                UtilMath.rotateAroundAxisY(multiply, Math.toRadians(90.0));
                clone.add(multiply);
                if (n2 % (this.amount / 6.0) == 0.0) {
                    final ReflectedArmorStand reflectedArmorStand = new ReflectedArmorStand(clone.clone().subtract(0.0, 0.5, 0.0));
                    reflectedArmorStand.setSmall(true);
                    reflectedArmorStand.setArms(true);
                    reflectedArmorStand.setVisible(true);
                    reflectedArmorStand.setRightArmPose(new EulerAngle(0.0, 0.0, Math.toRadians(323.0)));
                    reflectedArmorStand.setEquipment(0, new UtilItem(Material.LEGACY_WOOL, (byte)((c == Color.FUCHSIA) ? 10 : 5)).getItem());
                    reflectedArmorStand.setLocation(clone.clone().subtract(0.0, 0.5, 0.0));
                    reflectedArmorStand.spawnArmorStand();
                    this.portalblocks.put(reflectedArmorStand, n2);
                }
                this.circlePoints.add(new Location(clone.getWorld(), clone.getX(), clone.getY(), clone.getZ()));
                UtilParticle.sendParticle(clone, Particle.CLOUD, 0, multiply, 0.1f);
                clone.subtract(multiply);
            }
        }
        
        public void setDeny() {
            this.deny = true;
            new BukkitRunnable() {
                public void run() {
                    access(Portal.this, false);
                }
            }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 40L);
        }
        
        public void update() {
            this.circlePoints.forEach(location -> new ColoredParticle(Particle.REDSTONE, location, this.c.getRed(), this.c.getGreen(), this.c.getBlue()).send());
            final int n = 0;
            this.portalblocks.keySet().forEach(reflectedArmorStand -> {
                final Location location2;
                this.portalblocks.get(reflectedArmorStand);
                location2 = this.circlePoints.get(n);
                reflectedArmorStand.teleport(location2.clone().add(this.portalLocation.toVector().subtract(location2.toVector()).multiply(2.5)).subtract(0.0, 0.5, 0.0));
                if (n >= this.circlePoints.size() - 1) {
                    this.portalblocks.put(reflectedArmorStand, 0);
                }
                else {
                    this.portalblocks.put(reflectedArmorStand, n + 1);
                }
            });
        }
        
        public void remove() {
            this.portalblocks.keySet().forEach(reflectedArmorStand -> reflectedArmorStand.remove());
        }
        
        public Location getPortalLocation() {
            return this.portalLocation;
        }
        
        void access(final Portal portal, final boolean deny) {
            portal.deny = deny;
        }
    }
}
