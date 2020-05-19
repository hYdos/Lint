import me.hydos.lint.core.*;
import me.hydos.lint.core.client.LintClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

// DO NOT EVER TOUCH THIS.
// USED SO INTELLIJ SHUTS UP
// ABOUT UNUSED DECLARATIONS
public interface Blursed {

    @Environment(EnvType.CLIENT)
    static void onInitialize() {
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
