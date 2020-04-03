package me.hydos.lint.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import static me.hydos.lint.core.Dimensions.HAYKAM;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow
    private ClientWorld world;
    @Shadow
    @Final
    private static Identifier SUN;
    private static final Identifier COOLSUN = new Identifier("lint", "textures/environment/twin_sun.png");

    @Redirect(method = "renderSky", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/WorldRenderer;SUN:Lnet/minecraft/util/Identifier;"))
    private Identifier getCoolsun() {
        if (world.getDimension().getType() == HAYKAM) {
            return COOLSUN;
        } else {
            return SUN;
        }
    }

}
