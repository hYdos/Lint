package me.hydos.lint.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.Matrix4f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
