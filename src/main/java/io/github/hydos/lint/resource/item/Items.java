package io.github.hydos.lint.resource.item;

import io.github.hydos.lint.Lint;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public interface Items {

    Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.EPIC).maxCount(1));
    Item SPIRIT_ORB = new SpiritOrbItem(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.RARE).maxCount(1));

    LootFunctionType SET_RANDOM_SPIRIT = new LootFunctionType(new SetRandomSpiritFunction.Serializer());

    static void initialize() {
        Registry.register(Registry.ITEM, Lint.id("tater_essence"), TATER_ESSENCE);
        Registry.register(Registry.ITEM, Lint.id("spirit_orb"), SPIRIT_ORB);
        Registry.register(Registry.LOOT_FUNCTION_TYPE, Lint.id("set_random_spirit"), SET_RANDOM_SPIRIT);
    }
}
