package net.heroyn.heroynserverapi.nms;

import org.bukkit.*;
import java.util.*;
import java.lang.reflect.*;
import java.util.regex.*;

@SuppressWarnings({"unchecked", "rawtypes"})
public final class Reflection
{
    private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
    private static String NMS_PREFIX = Reflection.OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
    private static String VERSION = Reflection.OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
    private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}") ;
    
    public static <T> FieldAccessor<T> getField(final Class<?> clazz, final String s, final Class<T> clazz2) {
        return getField(clazz, s, clazz2, 0);
    }
    
    public static <T> FieldAccessor<T> getField(final String s, final String s2, final Class<T> clazz) {
        return getField(getClass(s), s2, clazz, 0);
    }
    
    public static <T> FieldAccessor<T> getField(final Class<?> clazz, final Class<T> clazz2, final int n) {
        return getField(clazz, null, clazz2, n);
    }
    
    public static <T> FieldAccessor<T> getField(final String s, final Class<T> clazz, final int n) {
        return getField(getClass(s), clazz, n);
    }
    
    private static <T> FieldAccessor<T> getField(final Class<?> clazz, final String s, final Class<T> clazz2, int n) {
        Field[] declaredFields;
        for (int length = (declaredFields = clazz.getDeclaredFields()).length, i = 0; i < length; ++i) {
            final Field field = declaredFields[i];
            if ((s == null || field.getName().equals(s)) && clazz2.isAssignableFrom(field.getType()) && n-- <= 0) {
                field.setAccessible(true);
                return new FieldAccessor<T>() {
					@Override
                    public T get(final Object o) {
                        try {
                            return (T)field.get(o);
                        }
                        catch (IllegalAccessException ex) {
                            throw new RuntimeException("Cannot access reflection.", ex);
                        }
                    }
                    
                    @Override
                    public void set(final Object o, final Object o2) {
                        try {
                            field.set(o, o2);
                        }
                        catch (IllegalAccessException ex) {
                            throw new RuntimeException("Cannot access reflection.", ex);
                        }
                    }
                    
                    @Override
                    public boolean hasField(final Object o) {
                        return field.getDeclaringClass().isAssignableFrom(o.getClass());
                    }
                };
            }
        }
        if (clazz.getSuperclass() != null) {
            return (FieldAccessor<T>)getField(clazz.getSuperclass(), s, (Class<Object>)clazz2, n);
        }
        throw new IllegalArgumentException("Cannot find field with type " + clazz2);
    }
    
    public static MethodInvoker getMethod(final String s, final String s2, final Class<?>... array) {
        return getTypedMethod(getClass(s), s2, null, array);
    }
    
    public static MethodInvoker getMethod(final Class<?> clazz, final String s, final Class<?>... array) {
        return getTypedMethod(clazz, s, null, array);
    }
    
    public static MethodInvoker getTypedMethod(final Class<?> clazz, final String s, final Class<?> clazz2, final Class<?>... array) {
        Method[] declaredMethods;
        for (int length = (declaredMethods = clazz.getDeclaredMethods()).length, i = 0; i < length; ++i) {
            final Method method = declaredMethods[i];
            if ((s == null || method.getName().equals(s)) && (clazz2 == null || method.getReturnType().equals(clazz2)) && Arrays.equals(method.getParameterTypes(), array)) {
                method.setAccessible(true);
                return new MethodInvoker() {
                    @Override
                    public Object invoke(final Object o, final Object... array) {
                        try {
                            return method.invoke(o, array);
                        }
                        catch (Exception ex) {
                            throw new RuntimeException("Cannot invoke method " + method, ex);
                        }
                    }
                };
            }
        }
        if (clazz.getSuperclass() != null) {
            return getMethod(clazz.getSuperclass(), s, array);
        }
        throw new IllegalStateException(String.format("Unable to find method %s (%s).", s, Arrays.asList(array)));
    }
    
    public static ConstructorInvoker getConstructor(final String s, final Class<?>... array) {
        return getConstructor(getClass(s), array);
    }
    
    public static ConstructorInvoker getConstructor(final Class<?> clazz, final Class<?>... array) {
		Constructor[] declaredConstructors;
        for (int length = (declaredConstructors = clazz.getDeclaredConstructors()).length, i = 0; i < length; ++i) {
            final Constructor constructor = declaredConstructors[i];
            if (Arrays.equals(constructor.getParameterTypes(), array)) {
                constructor.setAccessible(true);
                return new ConstructorInvoker() {
                    @Override
                    public Object invoke(final Object... array) {
                        try {
                            return constructor.newInstance(array);
                        }
                        catch (Exception ex) {
                            throw new RuntimeException("Cannot invoke constructor " + constructor, ex);
                        }
                    }
                };
            }
        }
        throw new IllegalStateException(String.format("Unable to find constructor for %s (%s).", clazz, Arrays.asList(array)));
    }
    
    public static Class<Object> getUntypedClass(final String s) {
        return (Class<Object>)getClass(s);
    }
    
    public static Class<?> getClass(final String s) {
        return getCanonicalClass(expandVariables(s));
    }
    
    public static Class<?> getMinecraftClass(final String s) {
        return getCanonicalClass(String.valueOf(Reflection.NMS_PREFIX) + "." + s);
    }
    
    public static Class<?> getCraftBukkitClass(final String s) {
        return getCanonicalClass(String.valueOf(Reflection.OBC_PREFIX) + "." + s);
    }
    
    private static Class<?> getCanonicalClass(final String s) {
        try {
            return Class.forName(s);
        }
        catch (ClassNotFoundException ex) {
            throw new IllegalArgumentException("Cannot find " + s, ex);
        }
    }
    
    private static String expandVariables(final String s) {
        final StringBuffer sb = new StringBuffer();
        final Matcher matcher = Reflection.MATCH_VARIABLE.matcher(s);
        while (matcher.find()) {
            final String group = matcher.group(1);
            String s2;
            if ("nms".equalsIgnoreCase(group)) {
                s2 = Reflection.NMS_PREFIX;
            }
            else if ("obc".equalsIgnoreCase(group)) {
                s2 = Reflection.OBC_PREFIX;
            }
            else {
                if (!"version".equalsIgnoreCase(group)) {
                    throw new IllegalArgumentException("Unknown variable: " + group);
                }
                s2 = Reflection.VERSION;
            }
            if (s2.length() > 0 && matcher.end() < s.length() && s.charAt(matcher.end()) != '.') {
                s2 = String.valueOf(s2) + ".";
            }
            matcher.appendReplacement(sb, Matcher.quoteReplacement(s2));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    
    public interface ConstructorInvoker
    {
        Object invoke(final Object... p0);
    }
    
    public interface FieldAccessor<T>
    {
        T get(final Object p0);
        
        void set(final Object p0, final Object p1);
        
        boolean hasField(final Object p0);
    }
    
    public interface MethodInvoker
    {
        Object invoke(final Object p0, final Object... p1);
    }
}
