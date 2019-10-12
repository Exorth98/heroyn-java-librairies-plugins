package net.heroyn.heroynapi.structures.structure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.heroyn.heroynapi.structures.structure.block.BlockStructure;

import java.util.List;

public class StructureJson extends Structure{

    private String json;

    public StructureJson(String json) {
        this.json = json;
    }

    public String getJson() {
        return json;
    }

    public StructureJson setJson(String json) {
        this.json = json;
        return this;
    }

    @Override
    public List<BlockStructure> getBlocks() {
        return deserialize();
    }

    private List<BlockStructure> deserialize(){
        final Gson gson = new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();
        return gson.fromJson(json, List.class);
    }

}
