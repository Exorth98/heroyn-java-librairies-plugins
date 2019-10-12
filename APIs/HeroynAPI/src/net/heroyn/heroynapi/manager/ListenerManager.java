 package net.heroyn.heroynapi.manager;
 
 import net.heroyn.heroynapi.HeroynAPI;
import net.heroyn.heroynapi.utils.LogUtils;

import java.lang.reflect.Constructor;
 import org.bukkit.event.Listener;
 
 public class ListenerManager
 {
   HeroynAPI pl;
   
   public ListenerManager(HeroynAPI pl)
   {
     this.pl = pl;
     registerAllListener();
   }
   
 
 
   static String name = "net.heroyn.heroynapi.Listener";
   
   public void registerAllListener()
   {
	 LogUtils.Log("§eChargement des Listeners...");
	 LogUtils.Log("§eListeners trouvés:");
     for (Class<?> cla : net.heroyn.heroynapi.utils.ClassGetter.getClassesForPackage(this.pl, name)) {
       try {
         if (!cla.getName().contains("$")) {
           Constructor<?> constructor = cla.getConstructor(new Class[] { HeroynAPI.class });
           
           HeroynAPI.getInstance().getServer().getPluginManager().registerEvents((Listener)constructor.newInstance(new Object[] { HeroynAPI.getInstance() }), HeroynAPI.getInstance());
           LogUtils.Log("§e - §a" + cla.getSimpleName().replace("Listener_", ""));
         }
       } catch (Exception e) {
    	   LogUtils.LogErreur("§e- §a" + cla.getSimpleName().replace("Listener_", "") + "§c Error");
       }
     }
     LogUtils.LogSuccess("§eChargement des Listener terminé !");
   }
 }