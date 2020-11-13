package io.github.hydos.lint.resource.item;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.function.ConditionalLootFunction;
import net.minecraft.loot.function.LootFunctionType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class SetRandomSpiritFunction extends ConditionalLootFunction {
    public SetRandomSpiritFunction(LootCondition[] conditions) {
        super(conditions);
    }

    @Override
    public LootFunctionType getType() {
        return Items.SET_RANDOM_SPIRIT;
    }

    @Override
    public ItemStack process(ItemStack stack, LootContext context) {
        if (stack.getItem() instanceof SpiritOrbItem) {
            Registry<Spirit> registry = context.getWorld().getRegistryManager().get(SpiritRegistry.SPIRIT_KEY);
            List<Identifier> ids = new ArrayList<>(registry.getIds());
            stack.getOrCreateTag().putString("Spirit", ids.get(context.getRandom().nextInt(ids.size())).toString());
        }
        return stack;
    }

    public static class Serializer extends ConditionalLootFunction.Serializer<SetRandomSpiritFunction> {
        @Override
        public SetRandomSpiritFunction fromJson(JsonObject object, JsonDeserializationContext context, LootCondition[] conditions) {
            return new SetRandomSpiritFunction(conditions);
        }
    }
}
