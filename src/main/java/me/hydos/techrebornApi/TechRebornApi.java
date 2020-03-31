package me.hydos.techrebornApi;

import me.hydos.techrebornApi.blocks.TRMachineBlock;
import sun.reflect.Reflection;
import techreborn.client.EGui;

public class TechRebornApi {

    private static TechRebornApi INSTANCE;

    public TechRebornApi(){
        INSTANCE = this;
    }

    public static EGui createGenericEGui() {
        return EGui.valueOf("mcp");
    }

    /**
     * Register blocks, etc
     */
    public void init(){

    }

    public static TechRebornApi getInstance(){//So it can be used everywhere in a mod
        return INSTANCE;
    }

    public void registerBlock(TRMachineBlock block){

    }

}
