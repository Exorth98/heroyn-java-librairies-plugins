package net.heroyn.heroynserverapi.utils;

import com.mojang.authlib.*;
import com.mojang.authlib.properties.*;
import java.lang.reflect.*;
import java.util.*;
import org.bukkit.inventory.*;
import org.bukkit.*;
import org.bukkit.inventory.meta.*;

public class UtilItem
{
    public Material m;
    public int id;
    public byte data;
    public String name;
    public List<String> lores;
    @SuppressWarnings("unused")
	private static final int RGB_MAX = 255;
    private static Random r;
    
    static {
        UtilItem.r = new Random();
    }
    
    public UtilItem(final Material m, final byte data, final String name) {
        this.m = m;
        this.data = data;
        this.name = name;
    }
    
    public UtilItem(final Material m, final byte data) {
        this.m = m;
        this.data = data;
        this.name = null;
    }
    
    public UtilItem(final int id, final byte data, final String name, final List<String> lores) {
        this.id = id;
        this.data = data;
        this.name = name;
        this.lores = lores;
    }
    
    public UtilItem(final int id, final byte data, final String name) {
        this.id = id;
        this.data = data;
        this.name = name;
    }
    
    public ItemStack getItem() {
		final ItemStack itemStack = new ItemStack((this.m == null) ? Material.BARRIER : this.m, 1, (short)this.data);
        if (this.name != null) {
            setDisplayName(itemStack, CC.colored(this.name));
        }
        if (this.lores != null) {
            setLore(itemStack, this.lores);
        }
        return itemStack;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getSkull(final String s, final String s2) {
        final ItemStack itemStack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        final SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), (String)null);
        final PropertyMap properties = gameProfile.getProperties();
        if (s2 != null) {
            itemMeta.setDisplayName(CC.colored(s2));
        }
        properties.put("textures", new Property("textures", s));
        try {
            final Field declaredField = itemMeta.getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            declaredField.set(itemMeta, gameProfile);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getSkull(final String s) {
        final ItemStack itemStack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        final SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), (String)null);
        gameProfile.getProperties().put("textures", new Property("textures", s));
        try {
            final Field declaredField = itemMeta.getClass().getDeclaredField("profile");
            declaredField.setAccessible(true);
            declaredField.set(itemMeta, gameProfile);
        }
        catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack getSkullOwner(final String owner) {
        final ItemStack itemStack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)SkullType.PLAYER.ordinal());
        final SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
        itemMeta.setOwner(owner);
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }
    
    @SuppressWarnings("deprecation")
	public static ItemStack convertSkull(String string) {
        string = "http://textures.minecraft.net/texture/" + new String(Base64.getDecoder().decode(string));
        final ItemStack itemStack = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
        if (string == null || string.isEmpty()) {
            return itemStack;
        }
        final SkullMeta itemMeta = (SkullMeta)itemStack.getItemMeta();
        itemMeta.setDisplayName(String.valueOf(CC.red) + "-");
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), (String)null);
        gameProfile.getProperties().put("textures", new Property("textures", new String(org.apache.commons.codec.binary.Base64.encodeBase64(String.format("{textures:{SKIN:{url:\"%s\"}}}", string).getBytes()))));
        Field declaredField = null;
        try {
            declaredField = itemMeta.getClass().getDeclaredField("profile");
        }
        catch (NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        assert declaredField != null;
        declaredField.setAccessible(true);
        try {
            declaredField.set(itemMeta, gameProfile);
        }
        catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }
    
    public static ItemStack setLore(final ItemStack itemStack, final List<String> list) {
        final ArrayList<String> lore = new ArrayList<String>();
        list.forEach(s -> lore.add(s.replace("&", "§")));
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore((List<String>)lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public static ItemStack setDisplayName(final ItemStack itemStack, final String s) {
        final ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(CC.colored(s));
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
    
    public static ItemStack getColorArmor(final Material material, final Color color) {
        final ItemStack itemStack = new ItemStack(material, 1);
        final LeatherArmorMeta itemMeta = (LeatherArmorMeta)itemStack.getItemMeta();
        itemMeta.setColor(color);
        itemStack.setItemMeta((ItemMeta)itemMeta);
        return itemStack;
    }
    
    public static Color getRandomColor() {
        return Color.fromRGB(UtilItem.r.nextInt(255), UtilItem.r.nextInt(255), UtilItem.r.nextInt(255));
    }
    
    public static ColorData getGreatRandomColor() {
        Color color = null;
        byte b = 0;
        switch (UtilMath.randomRange(0, 8)) {
            case 0: {
                b = 0;
                color = Color.WHITE;
                break;
            }
            case 1: {
                b = 1;
                color = Color.ORANGE;
                break;
            }
            case 2: {
                b = 2;
                color = Color.FUCHSIA;
                break;
            }
            case 3: {
                b = 4;
                color = Color.YELLOW;
                break;
            }
            case 4: {
                b = 5;
                color = Color.GREEN;
                break;
            }
            case 5: {
                b = 9;
                color = Color.NAVY;
                break;
            }
            case 6: {
                b = 10;
                color = Color.PURPLE;
                break;
            }
            case 7: {
                b = 11;
                color = Color.BLUE;
                break;
            }
            case 8: {
                b = 14;
                color = Color.RED;
                break;
            }
        }
        return new ColorData(b, color);
    }
    
    @SuppressWarnings("deprecation")
	public static boolean isSimilar(final ItemStack itemStack, final ItemStack itemStack2) {
        return itemStack.getItemMeta().getDisplayName().equals(CC.colored(itemStack2.getItemMeta().getDisplayName())) && itemStack.getType() == itemStack2.getType() && itemStack.getData().getData() == itemStack2.getData().getData();
    }
    
    public static class ColorData
    {
        private byte data;
        private Color color;
        
        public ColorData(final byte data, final Color color) {
            this.data = data;
            this.color = color;
        }
        
        public byte getData() {
            return this.data;
        }
        
        public Color getColor() {
            return this.color;
        }
    }
}
