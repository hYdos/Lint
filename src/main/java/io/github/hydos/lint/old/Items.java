package io.github.hydos.lint.old;

import java.util.List;

import io.github.hydos.lint.block.Blocks;
import io.github.hydos.lint.block.ReturnHomeBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public interface Items {
	Item TATER_ESSENCE = new TaterEssenceItem(new Item.Settings().group(ItemGroup.MATERIALS).rarity(Rarity.EPIC).maxCount(1));

	static void onInitialize() {
		Registry.register(Registry.ITEM, new Identifier("lint", "tater_essence"), TATER_ESSENCE);
	}

	class TaterEssenceItem extends Item {
		public TaterEssenceItem(Item.Settings settings) {
			super(settings);
		}

		@Override
		public boolean hasGlint(ItemStack stack) {
			return true;
		}

		@Override
		public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
			tooltip.add(new LiteralText("It radiates with a similar power"));
			tooltip.add(new LiteralText("to that which those shrines emit."));
		}

		@Override
		public ActionResult useOnBlock(ItemUsageContext context) {
			BlockPos pos = context.getBlockPos();
			World world = context.getWorld();

			if (world.getBlockState(pos) == Blocks.RETURN_HOME.getDefaultState()) {
				world.setBlockState(pos, Blocks.RETURN_HOME.getDefaultState().with(ReturnHomeBlock.ACTIVATED, true));
				context.getStack().decrement(1);
				return ActionResult.SUCCESS;
			}

			return ActionResult.PASS;
		}
	}
}
