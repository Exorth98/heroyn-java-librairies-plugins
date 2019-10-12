package net.heroyn.heroynapi.structures;

import net.heroyn.heroynapi.structures.structure.DespawnStructure;
import net.heroyn.heroynapi.structures.structure.Structure;
import org.bukkit.Location;
import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

public final class StructureAPI {

    private final static Map<Location, DespawnStructure> despawnStructures = new HashMap<>();

    public static void despawn(Location location){
        if(!despawnStructures.containsKey(location)) return;
        despawnStructures.get(location).spawn(location);
    }

    public static Map<Location, DespawnStructure> getDespawnStructures() {
        return despawnStructures;
    }

}
