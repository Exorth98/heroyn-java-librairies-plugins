package net.heroyn.heroynserverapi.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Color;

@SuppressWarnings("serial")
public class CC
{
    public static final Map<String, Color> COLOURS;
    public static String aqua;
    public static String black;
    public static String blue;
    public static String d_aqua;
    public static String d_blue;
    public static String d_gray;
    public static String d_green;
    public static String d_purple;
    public static String d_red;
    public static String gold;
    public static String gray;
    public static String green;
    public static String l_purple;
    public static String red;
    public static String white;
    public static String yellow;
    public static String bold;
    public static String italic;
    public static String magic;
    public static String reset;
    public static String underline;
    public static String strike;
    public static String arrow;
    public static String headkey;
    
    static {
        COLOURS = new HashMap<String, Color>() {
            {
                this.put("RED", Color.RED);
                this.put("GREEN", Color.GREEN);
                this.put("BLUE", Color.BLUE);
                this.put("WHITE", Color.AQUA);
                this.put("BLACK", Color.BLACK);
                this.put("FUSCHSIA", Color.FUCHSIA);
                this.put("LIME", Color.LIME);
                this.put("GRAY", Color.GRAY);
                this.put("MAROON", Color.MAROON);
                this.put("OLIVE", Color.OLIVE);
                this.put("NAVY", Color.NAVY);
                this.put("ORANGE", Color.ORANGE);
                this.put("PURPLE", Color.PURPLE);
                this.put("SILVER", Color.SILVER);
                this.put("TEAL", Color.TEAL);
                this.put("WHITE", Color.WHITE);
                this.put("YELLOW", Color.YELLOW);
            }
        };
        CC.aqua = new StringBuilder().append(ChatColor.AQUA).toString();
        CC.black = new StringBuilder().append(ChatColor.BLACK).toString();
        CC.blue = new StringBuilder().append(ChatColor.BLUE).toString();
        CC.d_aqua = new StringBuilder().append(ChatColor.DARK_AQUA).toString();
        CC.d_blue = new StringBuilder().append(ChatColor.DARK_BLUE).toString();
        CC.d_gray = new StringBuilder().append(ChatColor.DARK_GRAY).toString();
        CC.d_green = new StringBuilder().append(ChatColor.DARK_GREEN).toString();
        CC.d_purple = new StringBuilder().append(ChatColor.DARK_PURPLE).toString();
        CC.d_red = new StringBuilder().append(ChatColor.DARK_RED).toString();
        CC.gold = new StringBuilder().append(ChatColor.GOLD).toString();
        CC.gray = new StringBuilder().append(ChatColor.GRAY).toString();
        CC.green = new StringBuilder().append(ChatColor.GREEN).toString();
        CC.l_purple = new StringBuilder().append(ChatColor.LIGHT_PURPLE).toString();
        CC.red = new StringBuilder().append(ChatColor.RED).toString();
        CC.white = new StringBuilder().append(ChatColor.WHITE).toString();
        CC.yellow = new StringBuilder().append(ChatColor.YELLOW).toString();
        CC.bold = new StringBuilder().append(ChatColor.BOLD).toString();
        CC.italic = new StringBuilder().append(ChatColor.ITALIC).toString();
        CC.magic = new StringBuilder().append(ChatColor.MAGIC).toString();
        CC.reset = new StringBuilder().append(ChatColor.RESET).toString();
        CC.underline = new StringBuilder().append(ChatColor.UNDERLINE).toString();
        CC.strike = new StringBuilder().append(ChatColor.STRIKETHROUGH).toString();
        CC.arrow = "\u27bd";
        CC.headkey = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";
    }
    
    public static String rainbowlize(final String s) {
        final boolean b = false;
        String string = "";
        final String s2 = "123456789abcde";
        for (int i = 0; i < s.length(); ++i) {
            int j;
            do {
                j = new Random().nextInt(s2.length() - 1) + 1;
            } while (j == (b ? 1 : 0));
            string = String.valueOf(string) + ChatColor.RESET.toString() + ChatColor.getByChar(s2.charAt(j)) + s.charAt(i);
        }
        return string;
    }
    
    public static String colored(final String s) {
        return s.replace("&", "§");
    }
}
