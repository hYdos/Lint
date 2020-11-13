package io.github.hydos.lint.spirit;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import io.github.hydos.lint.Lint;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.util.Identifier;

public class SpiritComponentInitializer implements EntityComponentInitializer {
    private static final Identifier SPIRITS_ID = Lint.id("spirits");
    public static final ComponentKey<SpiritsComponent> SPIRITS = ComponentRegistryV3.INSTANCE.getOrCreate(SPIRITS_ID, SpiritsComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.registerForPlayers(SPIRITS, SpiritsComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
    } 
}
