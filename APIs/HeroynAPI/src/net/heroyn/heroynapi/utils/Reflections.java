package net.heroyn.heroynapi.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bukkit.Bukkit;

public final class Reflections
{
  private static String OBC_PREFIX = Bukkit.getServer().getClass().getPackage().getName();
  private static String NMS_PREFIX = OBC_PREFIX.replace("org.bukkit.craftbukkit", "net.minecraft.server");
  private static String VERSION = OBC_PREFIX.replace("org.bukkit.craftbukkit", "").replace(".", "");
  private static Pattern MATCH_VARIABLE = Pattern.compile("\\{([^\\}]+)\\}");
  
  private static String expandVariables(String name)
  {
    StringBuffer output = new StringBuffer();
    Matcher matcher = MATCH_VARIABLE.matcher(name);
    while (matcher.find())
    {
      String variable = matcher.group(1);
      String replacement;
      if ("nms".equalsIgnoreCase(variable))
      {
        replacement = NMS_PREFIX;
      }
      else
      {
        if ("obc".equalsIgnoreCase(variable))
        {
          replacement = OBC_PREFIX;
        }
        else
        {
          if ("version".equalsIgnoreCase(variable)) {
            replacement = VERSION;
          } else {
            throw new IllegalArgumentException("Unknown variable: " + variable);
          }
        }
      }
      if ((replacement.length() > 0) && (matcher.end() < name.length()) && (name.charAt(matcher.end()) != '.')) {
        replacement = replacement + ".";
      }
      matcher.appendReplacement(output, Matcher.quoteReplacement(replacement));
    }
    matcher.appendTail(output);
    return output.toString();
  }
  
  private static Class<?> getCanonicalClass(String canonicalName)
  {
    try
    {
      return Class.forName(canonicalName);
    }
    catch (ClassNotFoundException e)
    {
      throw new IllegalArgumentException("Cannot find " + canonicalName, e);
    }
  }
  
  public static Class<?> getClass(String lookupName)
  {
    return getCanonicalClass(expandVariables(lookupName));
  }
  
  public static ConstructorInvoker getConstructor(String className, Class<?>... params)
  {
    return getConstructor(getClass(className), params);
  }
  
  public static ConstructorInvoker getConstructor(Class<?> clazz, Class<?>... params)
  {
    @SuppressWarnings("rawtypes")
	Constructor[] arrayOfConstructor;
    int j = (arrayOfConstructor = clazz.getDeclaredConstructors()).length;
    for (int i = 0; i < j; i++)
    {
      Constructor<?> constructor = arrayOfConstructor[i];
      if (Arrays.equals(constructor.getParameterTypes(), params))
      {
        constructor.setAccessible(true);
        new ConstructorInvoker()
        {
          public Object invoke(Object... arguments)
          {
            try
            {
              return constructor.newInstance(arguments);
            }
            catch (Exception e)
            {
              throw new RuntimeException("Cannot invoke constructor " + constructor, e);
            }
          }
        };
      }
    }
    throw new IllegalStateException(String.format(
      "Unable to find constructor for %s (%s).", new Object[] { clazz, Arrays.asList(params) }));
  }
  
  public static Class<?> getCraftBukkitClass(String name)
  {
    return getCanonicalClass(OBC_PREFIX + "." + name);
  }
  
  public static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType)
  {
    return getField(target, name, fieldType, 0);
  }
  
  public static <T> FieldAccessor<T> getField(String className, String name, Class<T> fieldType)
  {
    return getField(getClass(className), name, fieldType, 0);
  }
  
  public static <T> FieldAccessor<T> getField(Class<?> target, Class<T> fieldType, int index)
  {
    return getField(target, null, fieldType, index);
  }
  
  public static <T> FieldAccessor<T> getField(String className, Class<T> fieldType, int index)
  {
    return getField(getClass(className), fieldType, index);
  }
  
  @SuppressWarnings("rawtypes")
private static <T> FieldAccessor<T> getField(Class<?> target, String name, Class<T> fieldType, int index)
  {
    Field[] arrayOfField;
    int j = (arrayOfField = target.getDeclaredFields()).length;
    for (int i = 0; i < j; i++)
    {
      Field field = arrayOfField[i];
      if (((name == null) || (field.getName().equals(name))) && (fieldType.isAssignableFrom(field.getType())) && (index-- <= 0))
      {
        field.setAccessible(true);
        
        new FieldAccessor()
        {
          @SuppressWarnings("unchecked")
		public T get(Object target)
          {
            try
            {
              return (T)field.get(target);
            }
            catch (IllegalAccessException e)
            {
              throw new RuntimeException("Cannot access reflection.", e);
            }
          }
          
          public void set(Object target, Object value)
          {
            try
            {
              ((Field) target).set(target, value);
            }
            catch (IllegalAccessException e)
            {
              throw new RuntimeException("Cannot access reflection.", e);
            }
          }
          
          public boolean hasField(Object target)
          {
            return ((Executable) target).getDeclaringClass().isAssignableFrom(target.getClass());
          }
        };
      }
    }
    if (target.getSuperclass() != null) {
      return getField(target.getSuperclass(), name, fieldType, index);
    }
    throw new IllegalArgumentException("Cannot find field with type " + fieldType);
  }
  
  public static MethodInvoker getMethod(String className, String methodName, Class<?>... params)
  {
    return getTypedMethod(getClass(className), methodName, null, params);
  }
  
  public static MethodInvoker getMethod(Class<?> clazz, String methodName, Class<?>... params)
  {
    return getTypedMethod(clazz, methodName, null, params);
  }
  
  public static Method getMethodSimply(Class<?> clazz, String method)
  {
    Method[] arrayOfMethod;
    int j = (arrayOfMethod = clazz.getMethods()).length;
    for (int i = 0; i < j; i++)
    {
      Method m = arrayOfMethod[i];
      if (m.getName().equals(method)) {
        return m;
      }
    }
    return null;
  }
  
  public static Class<?> getMinecraftClass(String name)
  {
    return getCanonicalClass(NMS_PREFIX + "." + name);
  }
  
  public static MethodInvoker getTypedMethod(Class<?> clazz, String methodName, Class<?> returnType, Class<?>... params)
  {
    Method[] arrayOfMethod;
    int j = (arrayOfMethod = clazz.getDeclaredMethods()).length;
    for (int i = 0; i < j; i++)
    {
      Method method = arrayOfMethod[i];
      if (((methodName != null) && (!method.getName().equals(methodName))) || (
        (returnType == null) || ((method.getReturnType().equals(returnType)) && 
        (Arrays.equals(method.getParameterTypes(), params)))))
      {
        method.setAccessible(true);
        new MethodInvoker()
        {
          public Object invoke(Object target, Object... arguments)
          {
            try
            {
              return ((Method) target).invoke(target, arguments);
            }
            catch (Exception e)
            {
              throw new RuntimeException("Cannot invoke method " + target, e);
            }
          }
        };
      }
    }
    if (clazz.getSuperclass() != null) {
      return getMethod(clazz.getSuperclass(), methodName, params);
    }
    throw new IllegalStateException(String.format(
      "Unable to find method %s (%s).", new Object[] { methodName, Arrays.asList(params) }));
  }
  
  public static Class<Object> getUntypedClass(String lookupName)
  {
    @SuppressWarnings("unchecked")
	Class<Object> clazz = (Class<Object>) getClass(lookupName);
    return clazz;
  }
  
public static <T> T newInstance(Class<T> type)
  {
    try
    {
      return (T)type.newInstance();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public static abstract interface ConstructorInvoker
  {
    public abstract Object invoke(Object... paramVarArgs);
  }
  
  public static abstract interface FieldAccessor<T>
  {
    public abstract T get(Object paramObject);
    
    public abstract void set(Object paramObject1, Object paramObject2);
    
    public abstract boolean hasField(Object paramObject);
  }
  
  public static abstract interface MethodInvoker
  {
    public abstract Object invoke(Object paramObject, Object... paramVarArgs);
  }
}
