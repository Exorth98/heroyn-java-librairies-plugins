package net.heroyn.heroynapi.structures.structure;

import net.heroyn.heroynapi.HeroynAPI;
import net.heroyn.heroynapi.structures.Position;
import net.heroyn.heroynapi.structures.StructureAPI;
import net.heroyn.heroynapi.structures.structure.block.*;
import net.heroyn.heroynapi.utils.RotationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;

public abstract class Structure {

    private List<BlockStructure> blocks = getBlocks();

    public abstract List<BlockStructure> getBlocks();

    public void spawn(Location location){
        spawn(location, SpawnType.NORMAL);
    }

    public void spawn(Location location, SpawnType type, Object...data){
        switch (type){
            case NORMAL:
                final List<BlockStructure> oldBlocks = new ArrayList<>();
                for (BlockStructure blockStructure : getBlocks()) {
                    if(!blockStructure.isDrop()) continue;
                    oldBlocks.add(new BlockStructure(blockStructure.getPosition()).setMaterial(getLocationByCenter(location, blockStructure.getPosition()).getBlock().getType()));
                }
                for (BlockStructure blockStructure : getBlocks()) {
                    if(blockStructure.isDrop()) continue;
                    oldBlocks.add(new BlockStructure(blockStructure.getPosition()).setMaterial(getLocationByCenter(location, blockStructure.getPosition()).getBlock().getType()));
                }
                StructureAPI.getDespawnStructures().put(location, new DespawnStructure(oldBlocks));
                for (BlockStructure blockStructure : getBlocks()) {
                    placeBlock(location, blockStructure);
                }
                break;
            case BLOCK_PER_BLOCK:
                if(data.length < 1) return;
                if(!(data[0] instanceof Integer)){
                    throw new IllegalArgumentException(data[0]+ "is not a number");
                }
                final List<BlockStructure> reamingBlocks = new ArrayList<>(getBlocks());
                Bukkit.getScheduler().runTaskTimer(HeroynAPI.getInstance(), () -> {
                    if(reamingBlocks.isEmpty()){ return; }
                    placeBlock(location, reamingBlocks.get(0));
                    reamingBlocks.remove(0);

                }, 1, (int) data[0]);
                break;
        }
    }

    protected Location getLocationByCenter(Location center, Position position){
        return new Location(center.getWorld(), (int) center.getX()+position.getX(), (int) center.getY()+position.getY(), (int) center.getZ()+position.getZ());
    }

    protected void placeBlock(Location location, BlockStructure blockStructure){
        final Block block = location.getWorld().getBlockAt((int) location.getX()+blockStructure.getPosition().getX(), (int) location.getY()+blockStructure.getPosition().getY(), (int) location.getZ()+blockStructure.getPosition().getZ());
        block.setType(blockStructure.getMaterial());
        if(blockStructure instanceof Actionable){
            ((Actionable) blockStructure).action(block);
        }
    }

    public enum SpawnType{
        NORMAL, BLOCK_PER_BLOCK
    }

}
