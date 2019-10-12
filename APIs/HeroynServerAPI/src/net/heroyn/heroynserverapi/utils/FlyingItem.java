package net.heroyn.heroynserverapi.utils;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.nms.entity.ReflectedArmorStand;

public class FlyingItem
{
    @SuppressWarnings("unused")
	private ItemStack[] materials;
    private ReflectedArmorStand item;
    private Location l;
    private boolean rotation;
    private double radius;
    private double speed;
    private boolean head;
    private int step;
    
    public FlyingItem(final Location l, final ItemStack[] materials) {
        this.materials = materials;
        this.l = l;
        (this.item = new ReflectedArmorStand(l)).setSmall(UtilMath.randomRange(0, 1) == 1);
        this.head = (UtilMath.randomRange(0, 1) == 1);
        this.item.setVisible(false);
        this.item.setEquipment(this.head ? 5 : 0, materials[new Random().nextInt(materials.length)]);
        if (this.head) {
            this.item.setHeadPose(new EulerAngle(Math.toRadians(UtilMath.randomRange(-30, 30)), Math.toRadians(UtilMath.randomRange(-30, 30)), Math.toRadians(UtilMath.randomRange(-30, 30))));
        }
        else {
            this.item.setRightArmPose(new EulerAngle(Math.toRadians(UtilMath.randomRange(-30, 30)), Math.toRadians(UtilMath.randomRange(-30, 30)), Math.toRadians(UtilMath.randomRange(-30, 30))));
        }
        this.item.spawnArmorStand();
        this.rotation = (UtilMath.randomRange(0, 5) <= 1);
        this.radius = UtilMath.randomRange(0.03f, 0.1f);
        this.speed = UtilMath.randomRange(1.0f, 3.0f);
        this.step = 120;
    }
    
    public void update() {
        if (this.item == null) {
            return;
        }
        if (this.rotation) {
            this.item.teleport(this.l.add(Math.cos(this.step * Math.toRadians(this.speed)) * this.radius, Math.toRadians(this.speed), Math.sin(this.step * Math.toRadians(this.speed)) * this.radius));
        }
        else {
            this.item.teleport(this.l.clone().add(0.0, Math.toRadians(this.speed), 0.0));
        }
        --this.step;
        if (this.step <= 0) {
            UtilParticle.sendParticle(this.item.getLocation().clone().add(0.0, 2.0, 0.0), Particle.CLOUD, 5, new Vector(0.1, 0.1, 0.1), 0.01f);
            this.item.remove();
            this.item = null;
        }
    }
    
    public ReflectedArmorStand getItem() {
        return this.item;
    }
}
