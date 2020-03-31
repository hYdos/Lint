package me.hydos.lint.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Slice;
import techreborn.init.TRContent;

@Mixin(TRContent.Ores.class)
public class BalanceMixin {

    @ModifyConstant(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=COPPER"), to = @At(value = "CONSTANT", args = "stringValue=GALENA")))
    private static int getRarity(int i) {
        switch (i) {
            case 8:         // Vein size
                return 32; // nice
            case 16:        // Veins per chunk
            case 20:        // Min Y
            case 60:        // Max Y
            default:
                return i;
        }
    }

    @ModifyConstant(method = "<clinit>", slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=BAUXITE"), to = @At(value = "CONSTANT", args = "stringValue=CINNABAR")))
    private static int getRarityButBauxite(int i) {
        switch (i) {
            case 6:         // Vein size
                return 16; // nice
            case 10:        // Veins per chunk & Min Y
            case 60:        // Max Y
            default:
                return i;
        }
    }

}
