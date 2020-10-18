package io.github.hydos.lint.item;

import io.github.hydos.lint.Lint;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class Items implements ModInitializer {

    public static final Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.EPIC).maxCount(1));

    @Override
    public void onInitialize() {
        Registry.register(Registry.ITEM, Lint.id("tater_essence"), TATER_ESSENCE);
    }
}
