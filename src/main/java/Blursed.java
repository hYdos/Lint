import me.hydos.lint.core.*;
import me.hydos.lint.core.client.LintClient;

public interface Blursed {

    static void no() {
        Lint.onInitialize();
        Blocks.onInitialize();
        Biomes.onInitialize();
        Dimensions.onInitialize();
        Features.onInitialize();
        Entities.onInitialize();
        Packets.onInitialize();
        Containers.onInitialize();
        LintClient.onInitializeClient();
    }
}
