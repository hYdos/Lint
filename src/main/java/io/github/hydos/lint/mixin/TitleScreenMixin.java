package io.github.hydos.lint.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.toast.SystemToast;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {

    @Inject(method = "<init>()V", at = @At("TAIL"))
    private void showSoundToast(CallbackInfo ci){
        if(MinecraftClient.getInstance().options.getSoundVolume(SoundCategory.MUSIC) == 0 || MinecraftClient.getInstance().options.getSoundVolume(SoundCategory.MASTER) == 0){
            MinecraftClient.getInstance().getToastManager().add(new SystemToast(SystemToast.Type.PACK_COPY_FAILURE, new LiteralText("Lint"), new LiteralText("Its recommended you have music on for the best experience")));
            //TODO: fix
        }
    }

}
