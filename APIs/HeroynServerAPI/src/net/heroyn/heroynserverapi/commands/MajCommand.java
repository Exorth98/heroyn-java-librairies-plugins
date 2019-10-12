package net.heroyn.heroynserverapi.commands;

import net.heroyn.heroynserverapi.updatesystem.ChatCommandPlayer;
import net.heroyn.heroynserverapi.updatesystem.Update;
import net.heroyn.heroynserverapi.updatesystem.VirtualMenuUpdateSystem;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.List;

public class MajCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String msg, String[] args) {
        if(sender instanceof Player){
            final Player player = (Player) sender;
            if(args.length >= 1){
                if(player.hasPermission("heroyn.update.admin")){
                    if(args[0].equalsIgnoreCase("create")){
                        if(ChatCommandPlayer.contains(player)){
                            player.sendMessage("§cVous êtes déjà en mode création");
                            return false;
                        }
                        final ItemStack stack = player.getInventory().getItemInMainHand();
                        if(stack == null || stack.getType() == Material.AIR){
                            player.sendMessage("§cVeuillez avoir un item valide dans votre main");
                            return false;
                        }
                        final ChatCommandPlayer chatPlayer = new ChatCommandPlayer(player);
                        chatPlayer.setState(ChatCommandPlayer.EnumType.NAME);
                        chatPlayer.getData().put("item", stack);
                        ChatCommandPlayer.getPlayers().add(chatPlayer);

                        player.sendMessage("§aPour annuler la création de la mise à jour, veuillez executer la commande §b/maj cancel");
                        player.sendMessage("§eVeuillez écrire le nom de la mise à jour");
                    }else if(args[0].equalsIgnoreCase("cancel")){
                        if(!ChatCommandPlayer.contains(player)){
                            player.sendMessage("§cVous n'êtes pas en mode création");
                            return false;
                        }
                        ChatCommandPlayer.getPlayers().remove(ChatCommandPlayer.byPlayer(player));
                        player.sendMessage("§aCréation de mise à jour annulée avec succès.");
                    }else if(args[0].equalsIgnoreCase("finish")){
                        if(!ChatCommandPlayer.contains(player)){
                            player.sendMessage("§cVous n'êtes pas en mode création");
                            return false;
                        }
                        final ChatCommandPlayer chatPlayer = ChatCommandPlayer.byPlayer(player);
                        if(!chatPlayer.getData().containsKey("name") || !chatPlayer.getData().containsKey("desc")){
                            player.sendMessage("§cVous n'avez pas terminer la création");
                            return false;
                        }
                        final Update update = new Update() {
                            @Override
                            public ItemStack getItemStack() {
                                return (ItemStack) chatPlayer.getData().get("item");
                            }

                            @Override
                            public String getName() {
                                return (String) chatPlayer.getData().get("name");
                            }

                            @Override
                            public List<String> getDescription() {
                                return (List<String>) chatPlayer.getData().get("desc");
                            }
                        };
                        VirtualMenuUpdateSystem.UPDATES.add(update);
                        ChatCommandPlayer.getPlayers().remove(ChatCommandPlayer.byPlayer(player));
                        Update.Utils.addUpdate(update);
                        player.sendMessage("§aMise à jour ajouté avec succès !");
                    }else if(args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")){
                        if(args.length < 2){
                            player.sendMessage("§cUsage: /maj remove <nom>");
                            return false;
                        }
                        String name = "";
                        for (int i = 0; i < args.length; i++) {
                            if(i == 0) continue;
                            if(i == args.length-1){
                                name += args[i];
                                continue;
                            }
                            name += args[i]+" ";
                        }
                        if(!VirtualMenuUpdateSystem.contains(name)){
                            player.sendMessage("§cLa mise à jour est introuvable");
                            return false;
                        }
                        final Update update = VirtualMenuUpdateSystem.getUpdateByName(name);
                        Update.Utils.removeUpdate(update);
                        VirtualMenuUpdateSystem.UPDATES.remove(update);
                        player.sendMessage("§aMise à jour suprimée");
                    }
                    return true;
                }
            }
            new VirtualMenuUpdateSystem().open(player);
        }
        return false;
    }

}
