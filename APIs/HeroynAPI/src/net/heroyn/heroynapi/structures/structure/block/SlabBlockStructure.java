package net.heroyn.heroynapi.structures.structure.block;

import net.heroyn.heroynapi.structures.Position;
import net.heroyn.heroynapi.utils.RotationUtils;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Slab;

public class SlabBlockStructure extends BlockStructure implements Actionable{

    private Slab.Type type = Slab.Type.BOTTOM;

    public SlabBlockStructure(Position position) {
        super(position);
    }

    public Slab.Type getType() {
        return type;
    }

    public SlabBlockStructure setType(Slab.Type type) {
        this.type = type;
        return this;
    }

    @Override
    public void action(Block block) {
        RotationUtils.setSlab(block, getType());
    }

}
