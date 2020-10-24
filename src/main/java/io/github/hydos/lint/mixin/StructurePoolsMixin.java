package io.github.hydos.lint.mixin;

import io.github.hydos.lint.world.structure.DungeonGenerator;
import net.minecraft.structure.pool.StructurePools;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StructurePools.class)
public class StructurePoolsMixin {

    @Inject(method = "<clinit>", at = @At("HEAD"))
    private static void initLintPools(CallbackInfo cir) {
        DungeonGenerator.init();
    }
}
