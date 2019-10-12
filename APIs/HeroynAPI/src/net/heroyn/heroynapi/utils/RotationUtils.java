package net.heroyn.heroynapi.utils;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Switch;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.material.Button;
import org.bukkit.material.Directional;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Stairs;

public final class RotationUtils {

    public static void setStairs(Block block, BlockFace face, boolean flipped) {
        if (block == null || !(block.getState().getData() instanceof Stairs))
            return;

        BlockState state = block.getState();

        Stairs stairs = (Stairs) state.getData();
        stairs.setFacingDirection(face);
        stairs.setInverted(flipped);

        state.setData(stairs);
        state.update(false, false);
    }

    public static void setRotation(Block block, BlockFace blockFace) {
        if (block == null || !(block.getState().getData() instanceof Directional))
            return;

        BlockState state = block.getState();

        MaterialData directional = state.getData();
        ((Directional) directional).setFacingDirection(blockFace);

        state.setData(directional);
        state.update(false, false);
    }

    public static void setSlab(Block block, Slab.Type type) {
        if (block == null || !(block.getState().getBlockData() instanceof Slab))
            return;

        BlockState state = block.getState();

        Slab slab = (Slab) state.getBlockData();
        slab.setType(type);

        state.setBlockData(slab);
        state.update(false, false);
    }

    public static void setTrapdoor(Block block, BlockFace face, Bisected.Half half, boolean open) {
        if (block == null || !(block.getState().getBlockData() instanceof Slab))
            return;

        BlockState state = block.getState();

        TrapDoor trapDoor = (TrapDoor) state.getBlockData();
        trapDoor.setFacing(face);
        trapDoor.setHalf(half);
        trapDoor.setOpen(open);
        trapDoor.setPowered(open);

        state.setBlockData(trapDoor);
        state.update(false, false);
    }

}
