package net.heroyn.heroynserverapi.nms.other;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import net.heroyn.heroynserverapi.utils.Skin;

public class ProfileLoader
{
    private final String name;
    private final String skinOwner;
    
    public ProfileLoader(final String s) {
        this(s, s);
    }
    
    public ProfileLoader(final String name, final String skinOwner) {
        this.name = name;
        this.skinOwner = skinOwner;
    }
    
    public GameProfile loadProfile(final Skin skin) {
        final GameProfile gameProfile = new GameProfile(UUID.randomUUID(), this.name);
        gameProfile.getProperties().put(this.name, new Property(this.name, skin.getTexture(), skin.getSignature()));
        return gameProfile;
    }
    
    @SuppressWarnings("unused")
	private void addProperties(final GameProfile gameProfile) {
        final String uuid = this.getUUID(this.skinOwner);
        try {
            final URLConnection openConnection = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false").openConnection();
            openConnection.setUseCaches(false);
            openConnection.setDefaultUseCaches(false);
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0");
            openConnection.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            openConnection.addRequestProperty("Pragma", "no-cache");
            @SuppressWarnings("resource")
			final JSONArray jsonArray = (JSONArray)((JSONObject)new JSONParser().parse(new Scanner(openConnection.getInputStream(), "UTF-8").useDelimiter("\\A").next())).get((Object)"properties");
            for (int i = 0; i < jsonArray.size(); ++i) {
                try {
                    final JSONObject jsonObject = (JSONObject)jsonArray.get(i);
                    final String s = (String)jsonObject.get((Object)"name");
                    final String s2 = (String)jsonObject.get((Object)"value");
                    final String s3 = jsonObject.containsKey((Object)"signature") ? ((String)jsonObject.get((Object)"signature")) : null;
                    if (s3 != null) {
                        gameProfile.getProperties().put(s, new Property(s, s2, s3));
                    }
                    else {
                        gameProfile.getProperties().put(s, new Property(s2, s));
                    }
                }
                catch (Exception ex) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", ex);
                }
            }
        }
        catch (Exception ex2) {}
    }
    
    @SuppressWarnings("deprecation")
	private String getUUID(final String s) {
        return Bukkit.getOfflinePlayer(s).getUniqueId().toString().replaceAll("-", "");
    }
}
