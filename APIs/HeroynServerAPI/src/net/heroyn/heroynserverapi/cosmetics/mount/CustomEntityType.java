package net.heroyn.heroynserverapi.cosmetics.mount;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import net.heroyn.heroynserverapi.cosmetics.mount.mounts.RideableSpider;
import net.minecraft.server.v1_13_R1.BiomeBase;
import net.minecraft.server.v1_13_R1.EntityInsentient;
import net.minecraft.server.v1_13_R1.EntitySpider;
import net.minecraft.server.v1_13_R1.EntityTypes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public enum CustomEntityType
{
    SPIDER("Spider", 52, EntityType.SPIDER, (Class<? extends EntityInsentient>)EntitySpider.class, (Class<? extends EntityInsentient>)RideableSpider.class);
    
    public static List<Entity> customEntities;
    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends EntityInsentient> nmsClass;
    private Class<? extends EntityInsentient> customClass;
    
    static {
        CustomEntityType.customEntities = new ArrayList<Entity>();
    }
    
    private CustomEntityType(final String name, final int id, final EntityType entityType, final Class<? extends EntityInsentient> nmsClass, final Class<? extends EntityInsentient> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getID() {
        return this.id;
    }
    
    public EntityType getEntityType() {
        return this.entityType;
    }
    
    public Class<? extends EntityInsentient> getNMSClass() {
        return this.nmsClass;
    }
    
    public Class<? extends EntityInsentient> getCustomClass() {
        return this.customClass;
    }
    
	public static void registerEntities() {
        CustomEntityType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final CustomEntityType entity = values[i];
            a(entity.getCustomClass(), entity.getName(), entity.getID());
        }
        BiomeBase[] biomes;
        try {
            biomes = (BiomeBase[])getPrivateStatic(BiomeBase.class, "biomes");
        }
        catch (Exception exc) {
            return;
        }
        BiomeBase[] array;
        for (int length2 = (array = biomes).length, j = 0; j < length2; ++j) {
            final BiomeBase biomeBase = array[j];
            if (biomeBase == null) {
                break;
            }
            String[] array2;
            for (int length3 = (array2 = new String[] { "at", "au", "av", "aw" }).length, k = 0; k < length3; ++k) {
                final String field = array2[k];
                try {
                    final Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    final List<BiomeBase.BiomeMeta> mobList = (List<BiomeBase.BiomeMeta>)list.get(biomeBase);
                    for (final BiomeBase.BiomeMeta meta : mobList) {
                        CustomEntityType[] values2;
                        for (int length4 = (values2 = values()).length, l = 0; l < length4; ++l) {
                            final CustomEntityType entity2 = values2[l];
                            if (entity2.getNMSClass().equals(meta.b)) {
                               // meta.b = entity2.getCustomClass();
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
	public static void unregisterEntities() {
        CustomEntityType[] values;
        for (int length = (values = values()).length, i = 0; i < length; ++i) {
            final CustomEntityType entity = values[i];
                try {
					((List<Entity>) getPrivateStatic(EntityTypes.class, "d")).remove(entity.getCustomClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
                try {
					((List<Entity>) getPrivateStatic(EntityTypes.class, "f")).remove(entity.getCustomClass());
				} catch (Exception e) {
					e.printStackTrace();
				}
        }
        CustomEntityType[] values2;
        for (int length2 = (values2 = values()).length, j = 0; j < length2; ++j) {
            final CustomEntityType entity = values2[j];
            try {
                a(entity.getNMSClass(), entity.getName(), entity.getID());
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        BiomeBase[] biomes;
        try {
            biomes = (BiomeBase[])getPrivateStatic(BiomeBase.class, "biomes");
        }
        catch (Exception exc) {
            return;
        }
        BiomeBase[] array;
        for (int length3 = (array = biomes).length, k = 0; k < length3; ++k) {
            final BiomeBase biomeBase = array[k];
            if (biomeBase == null) {
                break;
            }
            String[] array2;
            for (int length4 = (array2 = new String[] { "at", "au", "av", "aw" }).length, l = 0; l < length4; ++l) {
                final String field = array2[l];
                try {
                    final Field list = BiomeBase.class.getDeclaredField(field);
                    list.setAccessible(true);
                    final List<BiomeBase.BiomeMeta> mobList = (List<BiomeBase.BiomeMeta>)list.get(biomeBase);
                    for (final BiomeBase.BiomeMeta meta : mobList) {
                        CustomEntityType[] values3;
                        for (int length5 = (values3 = values()).length, n = 0; n < length5; ++n) {
                            final CustomEntityType entity2 = values3[n];
                            if (entity2.getCustomClass().equals(meta.b)) {
                               // meta.b = entity2.getNMSClass();
                            }
                        }
                    }
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }
    
	private static Object getPrivateStatic(final Class clazz, final String f) throws Exception {
        final Field field = clazz.getDeclaredField(f);
        field.setAccessible(true);
        return field.get(null);
    }
    
 
	private static void a(final Class paramClass, final String paramString, final int paramInt) {
        try {
            ((Map)getPrivateStatic(EntityTypes.class, "c")).put(paramString, paramClass);
            ((Map)getPrivateStatic(EntityTypes.class, "d")).put(paramClass, paramString);
            ((Map)getPrivateStatic(EntityTypes.class, "e")).put(paramInt, paramClass);
            ((Map)getPrivateStatic(EntityTypes.class, "f")).put(paramClass, paramInt);
            ((Map)getPrivateStatic(EntityTypes.class, "g")).put(paramString, paramInt);
        }
        catch (Exception ex) {}
    }
}
