package me.hydos.lint.core;

public interface Blursed {
    

    static void onInitialize() {
        Lint.onInitialize();
        Blocks.onInitialize();
        Biomes.onInitialize();
        Dimensions.onInitialize();
        Features.onInitialize();
        Entities.onInitialize();
        Packets.onInitialize();
        Containers.onInitialize();
        Sounds.onInitialize();
    }
}
