package io.github.hydos.lint.resource.item;

import java.util.List;

import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritComponentInitializer;
import io.github.hydos.lint.spirit.SpiritRegistry;
import io.github.hydos.lint.spirit.SpiritsComponent;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.sound.Sound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class SpiritOrbItem extends Item {
    public SpiritOrbItem(Settings settings) {
        super(settings);
    }
    
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        // Require tag
        ItemStack stack = user.getStackInHand(hand);
        if (!stack.hasTag()) return TypedActionResult.pass(stack);

        // Require valid identifier
        Identifier id = Identifier.tryParse(stack.getTag().getString("Spirit"));
        if (id == null) return TypedActionResult.fail(stack);

        // Require spirit in registry
        Registry<Spirit> registry = world.getRegistryManager().get(SpiritRegistry.SPIRIT_KEY);
        Spirit spirit = registry.get(id);
        if (spirit == null) return TypedActionResult.fail(stack);

        SpiritsComponent component = SpiritComponentInitializer.SPIRITS.get(user);
        if (component.hasSpirit(spirit)) {
            return TypedActionResult.fail(stack);
        }

        if (!world.isClient()) return TypedActionResult.consume(stack);
        component.addSpirit(spirit);

        user.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.PLAYERS, 1, 1);

        if (!user.abilities.creativeMode) {
            stack.decrement(1);
        }
        return TypedActionResult.consume(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (stack.hasTag()) {
            Identifier id = Identifier.tryParse(stack.getTag().getString("Spirit"));
            if (id != null) {
                Text spiritText = new TranslatableText("spirit." + id.getNamespace() + "." + id.getPath());
                tooltip.add(new TranslatableText("item.lint.spirit_orb.tooltip", spiritText).formatted(Formatting.GRAY).formatted(Formatting.ITALIC));
            }
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
