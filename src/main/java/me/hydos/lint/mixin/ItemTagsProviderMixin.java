package me.hydos.lint.mixin;

import me.hydos.lint.item.Items;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.server.AbstractTagProvider;
import net.minecraft.data.server.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemTagsProvider.class)
public abstract class ItemTagsProviderMixin extends AbstractTagProvider<Item> {

	protected ItemTagsProviderMixin(DataGenerator root, Registry<Item> registry) {
		super(root, registry);
	}

	@Inject(method = "configure", at = @At("TAIL"))
	private void registerCustomMusicDiscs(CallbackInfo ci){
		this.getOrCreateTagBuilder(ItemTags.MUSIC_DISCS).add(Items.OBOE_DISC, Items.OCEAN_DISC, Items.CORRUPT_FOREST_DISC, Items.MYSTICAL_FOREST_DISC);
	}

}
