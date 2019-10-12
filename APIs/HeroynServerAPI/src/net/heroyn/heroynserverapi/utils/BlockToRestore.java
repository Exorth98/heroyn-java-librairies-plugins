package net.heroyn.heroynserverapi.utils;

//import java.util.ArrayList;
//import java.util.List;
//
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.Material;
//import org.bukkit.block.Block;
//import org.bukkit.block.data.BlockData;
//import org.bukkit.plugin.Plugin;
//import org.bukkit.scheduler.BukkitRunnable;
//
//import net.heroyn.heroynserverapi.HeroynServerAPI;
//
//@SuppressWarnings("deprecation")
public class BlockToRestore
{
//    private static List<Block> blocks;
//    private static List<BlockToRestore> fakeblocks;
//    private Location location;
//    private int ID;
//    private byte DATA;
//    private int id;
//    private byte data;
//    
//    static {
//        BlockToRestore.blocks = new ArrayList<Block>();
//        BlockToRestore.fakeblocks = new ArrayList<BlockToRestore>();
//    }
//    
//	public BlockToRestore(final Location location) {
//        this.location = location.clone();
//        this.ID = location.getBlock().getType().getId();
//        this.DATA = location.getBlock().getData();
//    }
//    
//    public void setBlock(final int n, final byte data, final int n2) {
//        BlockToRestore.blocks.add(this.location.getBlock());
//        BlockToRestore.fakeblocks.add(this);
//        this.location.getBlock().setType(Material.getMaterial(n));
//        this.location.getBlock().setBlockData(data);
//        new BukkitRunnable() {
//            public void run() {
//                BlockToRestore.this.location.getBlock().setType(Material.getMaterial(BlockToRestore.this.ID));
//                BlockToRestore.this.location.getBlock().setData(BlockToRestore.this.DATA);
//                BlockToRestore.blocks.remove(BlockToRestore.this.location.getBlock());
//                BlockToRestore.fakeblocks.remove(this);
//            }
//        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), n2 * 20L);
//    }
//    
//    public boolean setFakeBlock(final int id, final byte data, final int n) {
//        if (BlockToRestore.blocks.contains(this.location.getBlock())) {
//            return false;
//        }
//        this.id = id;
//        this.data = data;
//        BlockToRestore.blocks.add(this.location.getBlock());
//        BlockToRestore.fakeblocks.add(this);
//        UtilLocation.getClosestPlayersFromLocation(this.location, 100.0).forEach(player -> player.sendBlockChange(this.location, id, data));
//        new BukkitRunnable() {
//            public void run() {
//                BlockToRestore.this.restoreBlock();
//            }
//        }.runTaskLater((Plugin)HeroynServerAPI.getInstance(), n * 20L);
//        return true;
//    }
//    
//    public boolean setFakeBlock(final int id, final byte data) {
//        if (BlockToRestore.blocks.contains(this.location.getBlock())) {
//            return false;
//        }
//        this.id = id;
//        this.data = data;
//        BlockToRestore.blocks.add(this.location.getBlock());
//        BlockToRestore.fakeblocks.add(this);
//        UtilLocation.getClosestPlayersFromLocation(this.location, Bukkit.getViewDistance()).forEach(player -> player.sendBlockChange(this.location, id, data));
//        return true;
//    }
//    
//    public void restoreBlock() {
//        BlockToRestore.blocks.remove(this.location.getBlock());
//        BlockToRestore.fakeblocks.remove(this);
//        Bukkit.getOnlinePlayers().forEach(player -> player.sendBlockChange(this.location, this.ID, this.DATA));
//    }
//    
//    public void updateFakeblock() {
//        UtilLocation.getClosestPlayersFromLocation(this.location, 90.0).forEach(player -> player.sendBlockChange(this.location, this.id, this.data));
//    }
//    
//    public void updateBlockData(final int id, final byte data) {
//        this.id = id;
//        this.data = data;
//    }
//    
//    public static List<Block> getBlocks() {
//        return BlockToRestore.blocks;
//    }
//    
//    public static List<BlockToRestore> getFakeblocks() {
//        return BlockToRestore.fakeblocks;
//    }
//    
//    public int getId() {
//        return this.id;
//    }
//    
//    public byte getData() {
//        return this.data;
//    }
//    
//    public Location getLocation() {
//        return this.location;
//    }
}
