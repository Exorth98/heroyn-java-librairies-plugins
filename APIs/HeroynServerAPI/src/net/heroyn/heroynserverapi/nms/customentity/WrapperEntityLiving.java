package net.heroyn.heroynserverapi.nms.customentity;

import net.minecraft.server.v1_13_R1.Entity;
import net.minecraft.server.v1_13_R1.EntityLiving;

public class WrapperEntityLiving extends WrapperEntity
{
    protected EntityLiving handle;
    
    public WrapperEntityLiving(final EntityLiving handle) {
        super((Entity)handle);
        this.handle = handle;
    }
    
    public float getRotationYawHead() {
        return this.handle.aQ;
    }
    
    public void setRotationYawHead(final float rotationYawHead) {
        this.handle.aQ = rotationYawHead;
    }
    
    public float getRenderYawOffset() {
        return this.handle.aO;
    }
    
    public void setRenderYawOffset(final float renderYawOffset) {
        this.handle.aO = renderYawOffset;
    }
    
//    public float getMoveStrafing() {
//        return this.handle.bf;
//    }
//    
//    public void setMoveStrafing(final float moveStrafing) {
//        this.handle.bf = moveStrafing;
//    }
//    
//    public float getMoveForward() {
//        return this.handle.bg;
//    }
//    
//    public void setMoveForward(final float moveForward) {
//        this.handle.bg = moveForward;
//    }
    
    public boolean isJumping() {
        return this.getField("be", EntityLiving.class, Boolean.class);
    }
    
    public void setJumping(final boolean jumping) {
        this.setField("be", EntityLiving.class, jumping);
    }
    
    public float getJumpMovementFactor() {
        return this.handle.aR;
    }
    
    public void setJumpMovementFactor(final float jumpMovementFactor) {
        this.handle.aR = jumpMovementFactor;
    }
    
    public float getPrevLimbSwingAmount() {
        return this.handle.aG;
    }
    
    public void setPrevLimbSwingAmount(final float prevLimbSwingAmount) {
        this.handle.aG = prevLimbSwingAmount;
    }
    
//    public float getLimbSwingAmount() {
//        return this.handle.aH;
//    }
//    
//    public void setLimbSwingAmount(final float limbSwingAmount) {
//        this.handle.aH = limbSwingAmount;
//    }
    
    public float getLimbSwing() {
        return this.handle.aJ;
    }
    
    public void setLimbSwing(final float limbSwing) {
        this.handle.aJ = limbSwing;
    }
    
//    public float getMoveSpeed() {
//        return this.handle.cy();
//    }
    
    public void setMoveSpeed(final float moveSpeed) {
        this.handle.k(moveSpeed);
    }
    
    @Override
    public EntityLiving getHandle() {
        return this.handle;
    }
}
