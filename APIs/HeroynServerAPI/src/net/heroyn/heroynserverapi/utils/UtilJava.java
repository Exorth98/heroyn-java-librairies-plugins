package net.heroyn.heroynserverapi.utils;

import java.lang.reflect.Array;

public class UtilJava
{
    public static Object getArray(final Class<?> clazz, final Object... array) {
        final Object instance = Array.newInstance(clazz, array.length);
        try {
            Integer n = 0;
            for (int length = array.length, i = 0; i < length; ++i) {
                Array.set(instance, n, array[i]);
                ++n;
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return instance;
    }
}
