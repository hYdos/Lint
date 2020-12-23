package io.github.hydos.lint.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Shadow private long backgroundFadeStart;

    private boolean displayedNotification = false;

    @Inject(method = "tick", at = @At("TAIL"))
    private void showSoundToast(CallbackInfo ci){
        if(backgroundFadeStart == 0 && !displayedNotification){
            if(MinecraftClient.getInstance().options.getSoundVolume(SoundCategory.MUSIC) == 0 || MinecraftClient.getInstance().options.getSoundVolume(SoundCategory.MASTER) == 0){
                MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.PACK_COPY_FAILURE, new LiteralText("Lint"), new LiteralText("Turn your sounds up!")));
                displayedNotification = true;
            }
        }
    }

}
