package net.heroyn.heroynapi.structures.structure.block;

import net.heroyn.heroynapi.structures.Position;
import net.heroyn.heroynapi.utils.RotationUtils;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class RotationBlockStructure extends BlockStructure implements Actionable{

    private BlockFace face;

    public RotationBlockStructure(Position position) {
        super(position);
    }

    public BlockFace getFace() {
        return face;
    }

    public RotationBlockStructure setFace(BlockFace face) {
        this.face = face;
        return this;
    }

    @Override
    public void action(Block block) {
        RotationUtils.setRotation(block, getFace());
    }

}
