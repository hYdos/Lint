import me.hydos.lint.core.*;
import me.hydos.lint.core.client.LintClient;

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
        Lint.onInitializeServer();
        LintClient.onInitializeClient();
        Packets.onInitialize();
        Sounds.onInitialize();
    }
}
