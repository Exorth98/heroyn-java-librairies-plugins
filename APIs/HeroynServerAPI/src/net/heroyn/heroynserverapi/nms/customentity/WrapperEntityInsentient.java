package net.heroyn.heroynserverapi.nms.customentity;

import net.minecraft.server.v1_13_R1.EntityInsentient;
import net.minecraft.server.v1_13_R1.EntityLiving;

public class WrapperEntityInsentient extends WrapperEntityLiving
{
    protected EntityInsentient handle;
    
    public WrapperEntityInsentient(final EntityInsentient handle) {
        super((EntityLiving)handle);
        this.handle = handle;
    }
    
    @Override
    public EntityInsentient getHandle() {
        return this.handle;
    }
}
