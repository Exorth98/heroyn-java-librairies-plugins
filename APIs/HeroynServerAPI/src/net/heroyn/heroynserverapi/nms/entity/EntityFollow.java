package net.heroyn.heroynserverapi.nms.entity;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import net.heroyn.heroynserverapi.nms.Reflection;
import net.heroyn.heroynserverapi.player.PlayerInfo;
import net.heroyn.heroynserverapi.utils.UtilMath;

@SuppressWarnings("unused")
public class EntityFollow
{
    private static Reflection.MethodInvoker getHandleMethod = Reflection.getMethod("{obc}.entity.CraftLivingEntity", "getHandle", (Class<?>[])new Class[0]);;
    private static Reflection.MethodInvoker getNavigationMethod = Reflection.getMethod(Reflection.getMinecraftClass("EntityInsentient"), "getNavigation", (Class<?>[])new Class[0]);;
    private static Reflection.MethodInvoker getA1Method = Reflection.getMethod(Reflection.getMinecraftClass("NavigationAbstract"), "a", Double.TYPE, Double.TYPE, Double.TYPE);
    private static Reflection.MethodInvoker getA2Method = Reflection.getMethod(Reflection.getMinecraftClass("NavigationAbstract"), "a", Reflection.getMinecraftClass("PathEntity"), Double.TYPE);
    private static Reflection.MethodInvoker getA3Method = Reflection.getMethod(Reflection.getMinecraftClass("NavigationAbstract"), "a", Double.TYPE);
    private Object livingEntity;
    private Object getNavigation;
    private Object path;
    private Entity entity;
	private Player player;
    private double maxDistance;
    private double speed;
    private Vector vector;
    
    public EntityFollow(final LivingEntity entity, final Player player, final double speed, final Vector vector, final double maxDistance) {
        this.entity = (Entity)entity;
        this.player = player;
        this.speed = speed;
        this.vector = vector;
        this.maxDistance = maxDistance;
        entity.setRemoveWhenFarAway(false);
        this.livingEntity = EntityFollow.getHandleMethod.invoke(entity, new Object[0]);
        this.getNavigation = EntityFollow.getNavigationMethod.invoke(this.livingEntity, new Object[0]);
        PlayerInfo.instanceOf(player).getEntityFollowList().add(this);
    }
    
    public EntityFollow(final LivingEntity livingEntity, final Player player, final double n) {
        new EntityFollow(livingEntity, player, n, new Vector(2, 0, 2), 4.0);
    }
    
    public EntityFollow(final LivingEntity livingEntity, final Player player, final double n, final boolean b) {
        final EntityFollow entityFollow = new EntityFollow(livingEntity, player, n, b ? new Vector(UtilMath.getRandomWithExclusion(-2, 2, 0), 0, UtilMath.getRandomWithExclusion(-2, 2, 0)) : new Vector(2, 0, 2), 4.0);
    }
    
    public void follow(final Player player) {
        if (player == null) {
            return;
        }
        if (!player.getLocation().getWorld().equals(this.entity.getWorld())) {
            this.entity.teleport(player.getLocation().add(2.0, 0.0, 2.0));
            this.livingEntity = EntityFollow.getHandleMethod.invoke(this.entity, new Object[0]);
            this.getNavigation = EntityFollow.getNavigationMethod.invoke(this.livingEntity, new Object[0]);
            return;
        }
        if (player.getLocation().distance(this.entity.getLocation()) <= 20.0) {
            if (player.getLocation().distance(this.entity.getLocation()) > 4.0) {
                this.path = EntityFollow.getA1Method.invoke(this.getNavigation, player.getLocation().getX() + this.vector.getX(), player.getLocation().getY(), player.getLocation().getZ() + this.vector.getZ());
                EntityFollow.getA2Method.invoke(this.getNavigation, this.path, this.speed);
                EntityFollow.getA3Method.invoke(this.getNavigation, this.speed);
            }
        }
        else if (player.isOnGround()) {
            this.entity.teleport(player.getLocation().add(2.0, 0.0, 2.0));
        }
    }
    
    public void unfollow(final PlayerInfo prodigyPlayer) {
        prodigyPlayer.getEntityFollowList().remove(this);
    }
    
    public Entity getEntity() {
        return this.entity;
    }
}
