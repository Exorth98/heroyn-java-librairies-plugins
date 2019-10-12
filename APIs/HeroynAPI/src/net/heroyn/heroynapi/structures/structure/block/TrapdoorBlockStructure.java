package net.heroyn.heroynapi.structures.structure.block;

import net.heroyn.heroynapi.structures.Position;
import net.heroyn.heroynapi.utils.RotationUtils;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;

public class TrapdoorBlockStructure extends RotationBlockStructure{

    private Bisected.Half half = Bisected.Half.BOTTOM;
    private boolean open;

    public TrapdoorBlockStructure(Position position) {
        super(position);
    }

    public Bisected.Half getHalf() {
        return half;
    }

    public TrapdoorBlockStructure setHalf(Bisected.Half half) {
        this.half = half;
        return this;
    }

    public boolean isOpen() {
        return open;
    }

    @Deprecated
    public TrapdoorBlockStructure setOpen(boolean open) {
        this.open = open;
        return this;
    }

    @Override
    public void action(Block block) {
        RotationUtils.setTrapdoor(block, getFace(), getHalf(), isOpen());
    }

}
