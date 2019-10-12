package net.heroyn.heroynapi.structures.structure.block;

import net.heroyn.heroynapi.structures.Position;
import net.heroyn.heroynapi.utils.RotationUtils;
import org.bukkit.block.Block;

public class StairsBlockStructure extends RotationBlockStructure{

    private boolean flipped;

    public StairsBlockStructure(Position position) {
        super(position);
    }

    public boolean isFlipped() {
        return flipped;
    }

    public StairsBlockStructure setFlipped(boolean flipped) {
        this.flipped = flipped;
        return this;
    }

    @Override
    public void action(Block block) {
        RotationUtils.setStairs(block, getFace(), isFlipped());
    }

}
