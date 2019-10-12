package net.heroyn.heroynserverapi.cosmetics.particle;

import java.util.Iterator;

import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.heroyn.heroynserverapi.player.PlayerInfo;

@SuppressWarnings("rawtypes")
public abstract class Particles
{
    protected Player player;
    protected Particle particle;
    protected PlayerInfo playerInfo;
    protected Enum type;
    
    
	public Particles(final Player player, final Particle particle, final Enum type) {
        this.player = player;
        this.playerInfo = PlayerInfo.instanceOf(player);
        this.particle = particle;
        this.type = type;
        int n = 0;
        final Iterator<PlayerInfo> iterator = PlayerInfo.getPlayerInfos().values().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getParticle() != null) {
                ++n;
            }
        }
        this.startPlayingParticle();
    }
    
    public abstract void update();
    
    public void startPlayingParticle() {
        if (this.playerInfo.getParticle() != null) {
            if (this.playerInfo.getParticle().getClass().equals(this.getClass())) {
                if (this.type != null && this.type != ((Entity) this.playerInfo.getParticle()).getType()) {
                    ((Particles) this.playerInfo.getParticle()).stopPlayingParticle();
                    this.playerInfo.setParticle(this);
                }
                else {
                    ((Particles) this.playerInfo.getParticle()).stopPlayingParticle();
                }
            }
            else {
                this.playerInfo.setParticle(this);
            }
        }
        else {
            this.playerInfo.setParticle(this);
        }
    }
    
    public void stopPlayingParticle() {
        this.playerInfo.setParticle(null);
    }
    
    public Particle getParticle() {
        return this.particle;
    }
    
    public Enum getType() {
        return this.type;
    }
    
    public static class CustomParticle
    {
        private Class<? extends Particles> particleClass;
        private ItemStack item;
        
        public CustomParticle(final ItemStack item, final Class<? extends Particles> particleClass) {
            this.particleClass = particleClass;
            this.item = item;
        }
        
        public CustomParticle(final ItemStack item) {
            this.item = item;
        }
        
        public Class<? extends Particles> getParticleClass() {
            return this.particleClass;
        }
        
        public ItemStack getItem() {
            return this.item;
        }
    }
}
