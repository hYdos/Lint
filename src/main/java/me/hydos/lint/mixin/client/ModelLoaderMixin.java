package me.hydos.lint.mixin.client;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.util.ModelIdentifier;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

	@Redirect(method = "loadModel", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"))
	private void silentFluidBlockstateErrors(Logger logger, String message, Object p0, Object p1){
		if(p1 instanceof ModelIdentifier){
			ModelIdentifier location = (ModelIdentifier) p1;
			if(location.getNamespace().equals("lint") && location.getVariant().startsWith("level=")) { // Must not be a fluid. warn about it
				logger.error(message, p0, p1);
			}
		}
	}
}
