package net.heroyn.heroynserverapi.nms.customentity;

import net.minecraft.server.v1_13_R1.EntityLiving;
import net.minecraft.server.v1_13_R1.EntityHuman;

public class WrapperEntityHuman extends WrapperEntityLiving
{
    protected EntityHuman handle;
    
    public WrapperEntityHuman(final EntityHuman handle) {
        super((EntityLiving)handle);
        this.handle = handle;
    }
    
    @Override
    public EntityHuman getHandle() {
        return this.handle;
    }
}
