package io.github.hydos.lint.mixin;

import io.github.hydos.doshysound.DosHySoundSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Mixin(SoundManager.class)
public class SoundManagerMixin {

    @Inject(method = "prepare", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/Resource;getInputStream()Ljava/io/InputStream;", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void registerLength(ResourceManager resourceManager, Profiler profiler, CallbackInfoReturnable<SoundManager.SoundList> cir, SoundManager.SoundList soundList, Iterator var4, String string, List list, Iterator var7, Resource resource) {
//        DosHySoundSystem dosHySoundSystem = new DosHySoundSystem();
//        try {
//            double length = dosHySoundSystem.calculateDuration(resource.getInputStream());
//            DosHySoundSystem.LOGGER.info(length);
//        } catch (IOException e) {
//            DosHySoundSystem.LOGGER.info("Lint's SoundSystem has encountered an io error calculating the length of " + resource.toString());
//            e.printStackTrace();
//        }
//        dosHySoundSystem.addMapElement()
    }

}
