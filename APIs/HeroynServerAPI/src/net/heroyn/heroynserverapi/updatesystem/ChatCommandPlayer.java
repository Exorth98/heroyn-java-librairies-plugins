package net.heroyn.heroynserverapi.updatesystem;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatCommandPlayer {

    private final static List<ChatCommandPlayer> PLAYERS = new ArrayList<>();

    private final Player player;
    private EnumType state;
    private final Map<String, Object> data = new HashMap<>();

    public ChatCommandPlayer(Player player) {
        this.player = player;
        state = EnumType.NONE;
    }

    public EnumType getState() {
        return state;
    }

    public void setState(EnumType state) {
        this.state = state;
    }

    public Player getPlayer() {
        return player;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public enum EnumType{
        NONE, NAME, DESC
    }

    public static boolean contains(Player player){
        for (ChatCommandPlayer commandPlayer : PLAYERS) {
            if(commandPlayer.getPlayer() == player) return true;
        }
        return false;
    }

    public static ChatCommandPlayer byPlayer(Player player){
        for (ChatCommandPlayer commandPlayer : PLAYERS) {
            if(commandPlayer.getPlayer() == player) return commandPlayer;
        }
        return null;
    }

    public static List<ChatCommandPlayer> getPlayers() {
        return PLAYERS;
    }

}
