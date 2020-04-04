import me.hydos.lint.core.*;
import me.hydos.lint.core.client.LintClient;

// Used to remove unused declaration warnings
public interface Blursed {

    static void no() {
        //      "me.hydos.lint.core.Lint::onInitialize",
        //      "me.hydos.lint.core.Blocks::onInitialize",
        //      "me.hydos.lint.core.Biomes::onInitialize",
        //      "me.hydos.lint.core.Dimensions::onInitialize",
        //      "me.hydos.lint.core.Features::onInitialize",
        //      "me.hydos.lint.core.Entities::onInitialize",
        //      "me.hydos.lint.core.Packets::onInitialize",
        //      "me.hydos.lint.core.Containers::onInitialize"
        //      "me.hydos.lint.core.client.LintClient::onInitialize"
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
