package net.heroyn.heroynserverapi.nms.customentity;

import java.lang.reflect.Field;

public abstract class WrapperBase
{
    protected Object handle;
    
    public WrapperBase(final Object handle) {
        this.handle = handle;
    }
    
    public <T> T getField(final String name, final Class<?> fieldClass, final Class<T> clazz) {
        final T value = null;
        try {
            final Field field = fieldClass.getDeclaredField(name);
            field.setAccessible(true);
            return clazz.cast(field.get(this.handle));
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return value;
        }
    }
    
    public <T> void setField(final String name, final Class<?> fieldClass, final T value) {
        try {
            final Field field = fieldClass.getDeclaredField(name);
            field.setAccessible(true);
            field.set(this.handle, value);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    public Object getHandle() {
        return this.handle;
    }
}
