package net.heroyn.heroynapi.structures.structure.block;

import net.heroyn.heroynapi.structures.Position;
import org.bukkit.Material;

public class BlockStructure{

    private Material material = Material.AIR;
    private Position position;
    private boolean drop;

    public BlockStructure(Position position) {
        this.position = position;
    }

    public Material getMaterial() {
        return material;
    }

    public BlockStructure setMaterial(Material block) {
        this.material = block;
        return this;
    }

    public Position getPosition() {
        return position;
    }

    public BlockStructure setPosition(Position position) {
        this.position = position;
        return this;
    }

    public boolean isDrop() {
        return drop;
    }

    public BlockStructure setDrop(boolean drop) {
        this.drop = drop;
        return this;
    }

}
