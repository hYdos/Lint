import me.hydos.lint.core.*;

// DO NOT EVER TOUCH THIS.
// USED SO INTELLIJ SHUTS UP
// ABOUT UNUSED DECLARATIONS
public interface Blursed {

    static void onInitialize() {
        PreInitialize.onInitialize();
        Biomes.onInitialize();
        Blocks.onInitialize();
        Containers.onInitialize();
        Dimensions.onInitialize();
        Entities.onInitialize();
        Features.onInitialize();
        Lint.onInitialize();
        Packets.onInitialize();
        Sounds.onInitialize();
    }
}
