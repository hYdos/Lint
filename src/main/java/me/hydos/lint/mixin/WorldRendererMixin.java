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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.hydos.lint.core.Dimensions.HAYKAM;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Final
    @Shadow
    private static Identifier MOON_PHASES;

    @Final
    @Shadow
    private TextureManager textureManager;

    @Final
    @Shadow
    private MinecraftClient client;

    private @Shadow
    ClientWorld world;

    private @Shadow
    VertexBuffer lightSkyBuffer;

    @Final
    VertexBuffer darkSkyBuffer;

    @Final
    VertexBuffer starsBuffer;

    @Final
    @Shadow
    private VertexFormat skyVertexFormat;

    private static final Identifier COOLSUN = new Identifier("lint", "textures/environment/twin_sun.png");

    @Inject(at=@At("HEAD"), method = "renderSky", cancellable = true)
    public void tatooine(MatrixStack matrixStack, float f, CallbackInfo ci){
        assert client.world != null;
        if (client.world.dimension.hasVisibleSky()) {
            if(client.world.getDimension().getType() == HAYKAM){
                ci.cancel();
                RenderSystem.disableTexture();
                Vec3d vec3d = this.world.method_23777(this.client.gameRenderer.getCamera().getBlockPos(), f);
                float g = (float)vec3d.x;
                float h = (float)vec3d.y;
                float i = (float)vec3d.z;
                BackgroundRenderer.setFogBlack();
                BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
                RenderSystem.depthMask(false);
                RenderSystem.enableFog();
                RenderSystem.color3f(g, h, i);
                this.lightSkyBuffer.bind();
                this.skyVertexFormat.startDrawing(0L);
                this.lightSkyBuffer.draw(matrixStack.peek().getModel(), 7);
                VertexBuffer.unbind();
                this.skyVertexFormat.endDrawing();
                RenderSystem.disableFog();
                RenderSystem.disableAlphaTest();
                RenderSystem.enableBlend();
                RenderSystem.defaultBlendFunc();
                float[] fs = this.world.dimension.getBackgroundColor(this.world.getSkyAngle(f), f);
                float s;
                float t;
                float p;
                float q;
                float r;
                if (fs != null) {
                    RenderSystem.disableTexture();
                    RenderSystem.shadeModel(7425);
                    matrixStack.push();
                    matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
                    s = MathHelper.sin(this.world.getSkyAngleRadians(f)) < 0.0F ? 180.0F : 0.0F;
                    matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(s));
                    matrixStack.multiply(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
                    float k = fs[0];
                    t = fs[1];
                    float m = fs[2];
                    Matrix4f matrix4f = matrixStack.peek().getModel();
                    bufferBuilder.begin(6, VertexFormats.POSITION_COLOR);
                    bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(k, t, m, fs[3]).next();

                    for(int o = 0; o <= 16; ++o) {
                        p = (float)o * 6.2831855F / 16.0F;
                        q = MathHelper.sin(p);
                        r = MathHelper.cos(p);
                        bufferBuilder.vertex(matrix4f, q * 120.0F, r * 120.0F, -r * 40.0F * fs[3]).color(fs[0], fs[1], fs[2], 0.0F).next();
                    }

                    bufferBuilder.end();
                    BufferRenderer.draw(bufferBuilder);
                    matrixStack.pop();
                    RenderSystem.shadeModel(7424);
                }

                RenderSystem.enableTexture();
                RenderSystem.blendFuncSeparate(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE, GlStateManager.SrcFactor.ONE, GlStateManager.DstFactor.ZERO);
                matrixStack.push();
                s = 1.0F - this.world.getRainGradient(f);
                RenderSystem.color4f(1.0F, 1.0F, 1.0F, s);
                matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
                matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(this.world.getSkyAngle(f) * 360.0F));
                Matrix4f matrix4f2 = matrixStack.peek().getModel();
                t = 30.0F;
                this.textureManager.bindTexture(COOLSUN);
                bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
                bufferBuilder.vertex(matrix4f2, -t, 100.0F, -t).texture(0.0F, 0.0F).next();
                bufferBuilder.vertex(matrix4f2, t, 100.0F, -t).texture(1.0F, 0.0F).next();
                bufferBuilder.vertex(matrix4f2, t, 100.0F, t).texture(1.0F, 1.0F).next();
                bufferBuilder.vertex(matrix4f2, -t, 100.0F, t).texture(0.0F, 1.0F).next();
                bufferBuilder.end();
                BufferRenderer.draw(bufferBuilder);
                t = 20.0F;
                this.textureManager.bindTexture(MOON_PHASES);
                int u = this.world.getMoonPhase();
                int v = u % 4;
                int w = u / 4 % 2;
                float x = (float)(v) / 4.0F;
                p = (float)(w) / 2.0F;
                q = (float)(v + 1) / 4.0F;
                r = (float)(w + 1) / 2.0F;
                bufferBuilder.begin(7, VertexFormats.POSITION_TEXTURE);
                bufferBuilder.vertex(matrix4f2, -t, -100.0F, t).texture(q, r).next();
                bufferBuilder.vertex(matrix4f2, t, -100.0F, t).texture(x, r).next();
                bufferBuilder.vertex(matrix4f2, t, -100.0F, -t).texture(x, p).next();
                bufferBuilder.vertex(matrix4f2, -t, -100.0F, -t).texture(q, p).next();
                bufferBuilder.end();
                BufferRenderer.draw(bufferBuilder);
                RenderSystem.disableTexture();
                float ab = this.world.method_23787(f) * s;
                if (ab > 0.0F) {
                    RenderSystem.color4f(ab, ab, ab, ab);
                    this.starsBuffer.bind();
                    this.skyVertexFormat.startDrawing(0L);
                    this.starsBuffer.draw(matrixStack.peek().getModel(), 7);
                    VertexBuffer.unbind();
                    this.skyVertexFormat.endDrawing();
                }

                RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.disableBlend();
                RenderSystem.enableAlphaTest();
                RenderSystem.enableFog();
                matrixStack.pop();
                RenderSystem.disableTexture();
                RenderSystem.color3f(0.0F, 0.0F, 0.0F);
                assert this.client.player != null;
                double d = this.client.player.getCameraPosVec(f).y - this.world.getSkyDarknessHeight();
                if (d < 0.0D) {
                    matrixStack.push();
                    matrixStack.translate(0.0D, 12.0D, 0.0D);
                    this.darkSkyBuffer.bind();
                    this.skyVertexFormat.startDrawing(0L);
                    this.darkSkyBuffer.draw(matrixStack.peek().getModel(), 7);
                    VertexBuffer.unbind();
                    this.skyVertexFormat.endDrawing();
                    matrixStack.pop();
                }

                if (this.world.dimension.hasGround()) {
                    RenderSystem.color3f(g * 0.2F + 0.04F, h * 0.2F + 0.04F, i * 0.6F + 0.1F);
                } else {
                    RenderSystem.color3f(g, h, i);
                }

                RenderSystem.enableTexture();
                RenderSystem.depthMask(true);
                RenderSystem.disableFog();
            }
        }
    }

}
