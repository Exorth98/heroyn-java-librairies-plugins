package net.heroyn.heroynapi.structures.structure;

import net.heroyn.heroynapi.structures.structure.block.BlockStructure;

import java.util.List;

public class DespawnStructure extends Structure{

    private final List<BlockStructure> staticBlocks;

    public DespawnStructure(List<BlockStructure> blockStructures) {
        staticBlocks = blockStructures;
    }

    @Override
    public List<BlockStructure> getBlocks() {
        return staticBlocks;
    }

}
