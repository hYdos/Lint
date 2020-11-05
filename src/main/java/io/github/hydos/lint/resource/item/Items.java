package io.github.hydos.lint.resource.item;

import io.github.hydos.lint.Lint;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public interface Items {

    Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.EPIC).maxCount(1));

    static void initialize() {
        Registry.register(Registry.ITEM, Lint.id("tater_essence"), TATER_ESSENCE);
    }
}
