import me.hydos.lint.core.*;

public interface Blursed {

    static void onInitialize() {
        Biomes.onInitialize();
        Blocks.onInitialize();
        Containers.onInitialize();
        Dimensions.onInitialize();
        Entities.onInitialize();
        Lint.onInitialize();
        Packets.onInitialize();
        Sounds.onInitialize();
    }
}
