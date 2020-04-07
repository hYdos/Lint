package me.hydos.lint.mixin;

import net.minecraft.client.gui.screen.CreditsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(CreditsScreen.class)
public class CreditsScreenMixin {

    @Shadow
    private List<String> credits;

    @Inject(method = "init", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screen/CreditsScreen;creditsHeight:I"))
    private void onInit(CallbackInfo callbackInfo) {
        credits.add(" ---- LINT CREDITS ----");
        credits.add("Firstly, credit to hydos and ramidzkh for epic amazing mod, making the models, most of the code, etc");
        credits.add("Secondly, Valoeghese and Draylar for helping out with world generation, and Notch for writing the original alpha world generation code");
        credits.add("Also, Valoeghese again for making the epic king tater boss music");
    }
}
