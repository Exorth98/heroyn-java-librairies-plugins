package net.heroyn.heroynserverapi.cosmetics.gadget.gadgets;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Color;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.Particle;
//import org.bukkit.Sound;
//import org.bukkit.block.Block;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.EntityType;
//import org.bukkit.entity.Player;
//import org.bukkit.plugin.Plugin;
//import org.bukkit.scheduler.BukkitRunnable;
//import org.bukkit.util.Vector;
//
//import net.heroyn.heroynserverapi.HeroynServerAPI;
//import net.heroyn.heroynserverapi.cosmetics.gadget.IGadget;
//import net.heroyn.heroynserverapi.nms.entity.ReflectedLivingEntity;
//import net.heroyn.heroynserverapi.nms.player.NMSPlayer;
//import net.heroyn.heroynserverapi.utils.BlockToRestore;
//import net.heroyn.heroynserverapi.utils.CC;
//import net.heroyn.heroynserverapi.utils.UtilBlock;
//import net.heroyn.heroynserverapi.utils.UtilItem;
//import net.heroyn.heroynserverapi.utils.UtilItem.ColorData;
//import net.heroyn.heroynserverapi.utils.UtilLocation;
//import net.heroyn.heroynserverapi.utils.UtilMath;
//import net.heroyn.heroynserverapi.utils.UtilParticle;

public class DiscoballGadget /*extends IGadget*/
{
//    private BlockToRestore block;
//    private Location loc;
//    private List<Block> ground;
//    private List<UUID> victims;
//    private List<ReflectedLivingEntity> sheeps;
//    private float lenght;
//    private double angularVelocity;
//    double rotation;
//    private int step;
//    
//    public DiscoballGadget(final Player player) {
//        super(player, new UtilItem(138, (byte) 0, "&bDiscoball").getItem(), 30);
//        this.ground = new ArrayList<Block>();
//        this.victims = new ArrayList<UUID>();
//        this.sheeps = new ArrayList<ReflectedLivingEntity>();
//        this.lenght = 10.0f;
//        this.angularVelocity = 0.07853981633974483;
//        this.rotation = 0.0;
//        this.step = 0;
//    }
//    
//    @Override
//    public boolean tryStartGadget() {
//        if (this.player.getLocation().add(0.0, 10.0, 0.0).getBlock().getType() != Material.AIR && UtilBlock.getIn2DRadius(this.player.getLocation().add(0.0, 10.0, 0.0), 5.0).stream().filter(block -> block.getType() != Material.AIR).findAny().isPresent()) {
//            this.player.sendMessage(CC.red+"Il n'y a pas assez d'espace !");
//            return false;
//        }
//        return true;
//    }
//    
//	@Override
//    public void start() {
//        this.loc = this.player.getLocation().add(0.0, 10.0, 0.0);
//        (this.block = new BlockToRestore(this.loc)).setFakeBlock(95, (byte)0);
//        final Block block2 = this.loc.getBlock();
//        UtilBlock.getInRadius(this.player.getLocation(), 6.0).forEach(block -> {
//            block.getLocation().add(0.0, 1.0, 0.0).getBlock();
//            if (block.getType() != Material.AIR && block2.getType() == Material.AIR && block.getType().isSolid()) {
//                this.ground.add(block);
//            }
//            return;
//        });
//        for (int i = 0; i < 3; ++i) {
//            final ReflectedLivingEntity reflectedLivingEntity = new ReflectedLivingEntity(this.player.getLocation().add((double)UtilMath.randomRange(-6, 6), 0.0, (double)UtilMath.randomRange(-6, 6)), EntityType.SHEEP);
//            reflectedLivingEntity.spawnLivingEntity();
//            this.sheeps.add(reflectedLivingEntity);
//        }
//        new BukkitRunnable() {
//            public void run() {
//                DiscoballGadget.this.stop();
//            }
//        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), 200L);
//    }
//    
//	@Override
//    public void update() {
//        super.update();
//        if (this.block == null) {
//            return;
//        }
//        final Location add = this.loc.getBlock().getLocation().add(0.5, 0.0, 0.5);
//        final Vector randomVectorline = UtilMath.getRandomVectorline();
//        this.rotation = this.step * this.angularVelocity;
//        randomVectorline.setY(-Math.abs(randomVectorline.getY()));
//        for (int i = 0; i < 60; ++i) {
//            final Vector multiply = randomVectorline.clone().multiply(i * this.lenght / 60.0f);
//            UtilMath.rotateVector(multiply, this.rotation, this.rotation, this.rotation);
//            UtilParticle.sendParticle(add.add(multiply), Particle.REDSTONE, 1, new Vector(0, 0, 0), 1.0f);
//            add.subtract(multiply);
//        }
//        this.ground.forEach(block -> {
//            if (UtilMath.randomRange(0, 10) >= 9) {
//                UtilParticle.sendParticle(block.getLocation().add(0.0, 1.0, 0.0), Particle.CLOUD, 1, new Vector(0.0f, 1.0f, 0.0f), 0.01f);
//            }
//            return;
//        });
//        if (this.step % 5 == 0) {
//            this.block.getLocation().getWorld().playSound(this.loc, Sound.BLOCK_NOTE_BASEDRUM, 2.0f, 1.0f);
//            this.block.getLocation().getWorld().playSound(this.loc, Sound.BLOCK_NOTE_HAT, 2.0f, 1.0f);
//            this.block.updateBlockData(95, UtilItem.getGreatRandomColor().getData());
//            this.block.updateFakeblock();
//            final UtilItem.ColorData colorData = new ColorData((byte) 0, Color.RED);
//            UtilLocation.getClosestPlayersFromLocation(this.loc, 20.0).forEach(player -> {
//                this.victims.add(player.getUniqueId());
//                if (player.isOnGround()) player.setVelocity(new Vector(0, 0.2, 0));
//                NMSPlayer.equipPlayer((Entity)player, 2, UtilItem.getColorArmor(Material.LEATHER_BOOTS, colorData.getColor()), false);
//                NMSPlayer.equipPlayer((Entity)player, 3, UtilItem.getColorArmor(Material.LEATHER_LEGGINGS, colorData.getColor()), false);
//                NMSPlayer.equipPlayer((Entity)player, 4, UtilItem.getColorArmor(Material.LEATHER_CHESTPLATE, colorData.getColor()), false);
//                NMSPlayer.equipPlayer((Entity)player, 5, UtilItem.getColorArmor(Material.LEATHER_HELMET, colorData.getColor()), false);
//                return;
//            });
//        }
//        if (this.step % 20 == 0) {
//            this.sheeps.forEach(reflectedLivingEntity -> {
//                reflectedLivingEntity.setPitchRotation(UtilMath.randomRange(-60, 60));
//                reflectedLivingEntity.setYawRotation(UtilMath.randomRange(-60, 60));
//                return;
//            });
//            this.block.getLocation().getWorld().playSound(this.loc, Sound.BLOCK_NOTE_SNARE, 2.0f, 1.0f);
//        }
//        if (this.step % 15 == 0 || this.step % 20 == 0) {
//            this.block.getLocation().getWorld().playSound(this.loc, Sound.BLOCK_NOTE_PLING, 2.0f, 1.0f);
//        }
//        ++this.step;
//    }
//    
//    @SuppressWarnings("unused")
//	@Override
//    public void stop() {
//        if (this.block != null) {
//            this.block.restoreBlock();
//        }
//        final Player player = null;
//        this.victims.forEach(uuid -> {
//            Bukkit.getPlayer(uuid);
//            if (player == null) {
//                return;
//            }
//            else {
//                NMSPlayer.equipPlayer((Entity)player, 2, player.getInventory().getBoots(), false);
//                NMSPlayer.equipPlayer((Entity)player, 3, player.getInventory().getLeggings(), false);
//                NMSPlayer.equipPlayer((Entity)player, 4, player.getInventory().getChestplate(), false);
//                NMSPlayer.equipPlayer((Entity)player, 5, player.getInventory().getHelmet(), false);
//                return;
//            }
//        });
//        this.sheeps.forEach(reflectedLivingEntity -> reflectedLivingEntity.removeLivingEntity());
//        this.sheeps.clear();
//        this.victims.clear();
//        this.block = null;
//    }
}
