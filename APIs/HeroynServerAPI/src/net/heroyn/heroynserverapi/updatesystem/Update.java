package net.heroyn.heroynserverapi.updatesystem;

import net.heroyn.heroynapi.config.ConfigManager;
import net.heroyn.heroynserverapi.HeroynServerAPI;
import org.bukkit.inventory.ItemStack;
import java.util.*;

public interface Update {

    static Update newUpdate(ItemStack stack, String name, List<String> description){
        return new Update() {
            @Override
            public ItemStack getItemStack() {
                return stack;
            }

            @Override
            public String getName() {
                return name;
            }

            @Override
            public List<String> getDescription() {
                return description;
            }
        };
    }

    ItemStack getItemStack();
    String getName();
    List<String> getDescription();

    default String string(){
        return "["+getName()+"; "+getItemStack()+"; "+getDescription()+"]";
    }

    class Utils{

        private static final ConfigManager manager = new ConfigManager(HeroynServerAPI.getInstance(), "", "updates");

        public static void initFile(){
            manager.setupAndSave();
            for (int i = 0; i < 7; i++) {
                if(!manager.getCustomConfig().contains("updates."+i)){
                    manager.getCustomConfig().set("updates."+i, "null");
                    manager.saveCustomConfig();
                }
            }
            VirtualMenuUpdateSystem.UPDATES.addAll(getList());
            for (Update update : VirtualMenuUpdateSystem.UPDATES) {
                System.out.println(update.string());
            }
        }

        private static List<Update> getList(){
            List<Update> updates = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                if(!manager.getCustomConfig().contains("updates."+i)) continue;
                if(manager.getCustomConfig().get("updates."+i) instanceof String){
                    if(manager.getCustomConfig().getString("updates."+i).equalsIgnoreCase("null")) continue;
                }
                final ItemStack stack = (ItemStack) manager.getCustomConfig().get("updates."+i+".stack");
                final String name = manager.getCustomConfig().getString("updates."+i+".name");
                final List<String> description = manager.getCustomConfig().getStringList("updates."+i+".desc");
                updates.add(newUpdate(stack, name, description));
            }
            return updates;
        }

        public static void addUpdate(Update update){
            final List<Update> updates = getList();
            updates.add(update);
            for (int i = 0; i < 7; i++) {
                if(i >= updates.size()) continue;
                manager.getCustomConfig().set("updates."+i+".stack", updates.get(i).getItemStack());
                manager.getCustomConfig().set("updates."+i+".name", updates.get(i).getName());
                manager.getCustomConfig().set("updates."+i+".desc", updates.get(i).getDescription());
                manager.saveCustomConfig();
            }
        }

        public static void removeUpdate(Update update){
            int index = getConfigIndex(update);
            manager.getCustomConfig().set("updates."+index, "null");
            manager.saveCustomConfig();
        }

        private static int getConfigIndex(Update update){
            for (int i = 0; i < 7; i++) {
                if(!(manager.getCustomConfig().get("updates."+i) instanceof String)){
                    if(manager.getCustomConfig().getString("updates."+i).equalsIgnoreCase("null")) continue;
                }
                if(manager.getCustomConfig().getString("updates."+i+".name").equalsIgnoreCase(update.getName())) return i;
            }
            return 0;
        }

    }

}
