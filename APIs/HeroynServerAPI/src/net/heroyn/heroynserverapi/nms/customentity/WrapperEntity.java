package net.heroyn.heroynserverapi.nms.customentity;

import net.minecraft.server.v1_13_R1.Entity;

public class WrapperEntity extends WrapperBase
{
    protected Entity handle;
    
    public WrapperEntity(final Entity handle) {
        super(handle);
        this.handle = handle;
    }
    
    public float getStepHeight() {
        return (float) this.handle.P;
    }
    
    public void setStepHeight(final float stepHeight) {
        this.handle.P = stepHeight;
    }
    
//    public boolean canPassengerSteer() {
//        return this.handle.ba() != null;
//    }
    
    @Override
    public Entity getHandle() {
        return this.handle;
    }
}
