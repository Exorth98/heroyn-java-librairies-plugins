package net.heroyn.heroynserverapi.cosmetics.mount.mounts;

//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Player;
//
//import net.heroyn.heroynserverapi.cosmetics.mount.MountManager;
//import net.heroyn.heroynserverapi.nms.customentity.EntityBase;
//import net.heroyn.heroynserverapi.nms.customentity.IMountCustomEntity;
//import net.heroyn.heroynserverapi.nms.customentity.WrapperEntityHuman;
//import net.heroyn.heroynserverapi.nms.customentity.WrapperEntityInsentient;
//import net.minecraft.server.v1_13_R1.EntityHuman;
//import net.minecraft.server.v1_13_R1.EntityInsentient;
//import net.minecraft.server.v1_13_R1.EntityLiving;
import net.minecraft.server.v1_13_R1.EntitySpider;
//import net.minecraft.server.v1_13_R1.MathHelper;
import net.minecraft.server.v1_13_R1.World;

public class RideableSpider extends EntitySpider /*implements IMountCustomEntity, EntityBase*/
{
    public RideableSpider(final World world) {
        super(world);
    }
//    
//    public void e(final float sideMot, final float forMot) {
//        EntityHuman passenger = null;
//        if (!this.passengers.isEmpty()) {
//            passenger = (EntityHuman) this.passengers.get(0);
//        }
//        if (passenger == null || !(passenger instanceof EntityHuman)) {
//            final Player rider = (Player)passenger;
//            MountManager.shouldDie((EntityLiving)this, rider);
//            return;
//        }
//        ride(sideMot, forMot, passenger, (EntityInsentient)this);
//    }
//    
//    public void g_(final float sideMot, final float forMot) {
//        super.g(sideMot, forMot);
//    }
//    
//    public float getSpeed() {
//        return 1.75f;
//    }
//    
//    public boolean canFly() {
//        return false;
//    }
//    
//    public Entity getEntity() {
//        return (Entity)this.getBukkitEntity();
//    }
//    
//    static void ride(float sideMot, float forMot, final EntityHuman passenger, final EntityInsentient entity) {
//        if (!(entity instanceof EntityBase)) {
//            throw new IllegalArgumentException("The entity field should implements EntityBase");
//        }
//        final EntityBase entityBase = (EntityBase)entity;
//        final WrapperEntityInsentient wEntity = new WrapperEntityInsentient(entity);
//        final WrapperEntityHuman wPassenger = new WrapperEntityHuman(passenger);
//        if (passenger != null) {
//            final float n = passenger.yaw % 360.0f;
//            entity.yaw = n;
//            entity.lastYaw = n;
//            entity.pitch = passenger.pitch * 0.5f % 360.0f;
//            wEntity.setRenderYawOffset(entity.yaw);
//            wEntity.setRotationYawHead(entity.yaw);
//            sideMot = wPassenger.getMoveStrafing() * 0.25f;
//            forMot = wPassenger.getMoveForward() * 0.5f;
//            if (forMot <= 0.0f) {
//                forMot *= 0.25f;
//            }
//            wEntity.setJumping(wPassenger.isJumping());
//            if (wPassenger.isJumping() && (entity.onGround || entityBase.canFly())) {
//                entity.motY = 0.4;
//                final float f2 = MathHelper.sin(entity.yaw * 0.017453292f);
//                final float f3 = MathHelper.cos(entity.yaw * 0.017453292f);
//                entity.motX += -0.4f * f2;
//                entity.motZ += 0.4f * f3;
//            }
//            wEntity.setStepHeight(1.0f);
//            wEntity.setJumpMovementFactor(wEntity.getMoveSpeed() * 0.1f);
//            wEntity.setRotationYawHead(entity.yaw);
//            if (wEntity.canPassengerSteer()) {
//                final float speed = (float)MountManager.mountSpeed;
//                wEntity.setMoveSpeed(speed);
//                entityBase.g_(sideMot, forMot);
//            }
//            wEntity.setPrevLimbSwingAmount(wEntity.getLimbSwingAmount());
//            final double dx = entity.locX - entity.lastX;
//            final double dz = entity.locZ - entity.lastZ;
//            float f4 = MathHelper.sqrt(dx * dx + dz * dz) * 4.0f;
//            if (f4 > 1.0f) {
//                f4 = 1.0f;
//            }
//            wEntity.setLimbSwingAmount(wEntity.getLimbSwingAmount() + (f4 - wEntity.getLimbSwingAmount()) * 0.4f);
//            wEntity.setLimbSwing(wEntity.getLimbSwing() + wEntity.getLimbSwingAmount());
//        }
//        else {
//            wEntity.setStepHeight(0.5f);
//            wEntity.setJumpMovementFactor(0.02f);
//            entityBase.g_(sideMot, forMot);
//        }
//    }
//    
//    public void removeAi() {
//        this.setNoAI(false);
//    }
}
