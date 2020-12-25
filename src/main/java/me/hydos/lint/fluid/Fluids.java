package me.hydos.lint.fluid;

import me.hydos.lint.Lint;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;
import java.util.Map;

public class Fluids {

	public static final Map<Identifier, FluidEntry> MOLTEN_FLUID_MAP = new HashMap<>();

	public static void register() {
		MOLTEN_FLUID_MAP.put(Lint.id("iron"), new FluidEntry(Items.IRON_NUGGET, Items.IRON_INGOT, Blocks.IRON_BLOCK.asItem(), "iron", 0x44FFFFFF));
		MOLTEN_FLUID_MAP.put(Lint.id("gold"), new FluidEntry(Items.GOLD_NUGGET, Items.GOLD_INGOT, Blocks.GOLD_BLOCK.asItem(), "gold", 0x44ffdd30));
	}


	public static class FluidEntry {
		private final Item nugget;
		private final Item ingot;
		private final Item block;
		private final String materialName;
		private final int colour;

		private final MoltenMetalFluid.Still stillFluid;
		private final MoltenMetalFluid.Flowing flowingFluid;

		public FluidEntry(Item nugget, Item ingot, Item block, String materialName, int colour) {
			this.nugget = nugget;
			this.ingot = ingot;
			this.block = block;
			this.materialName = materialName;
			this.colour = colour;

			this.stillFluid = Registry.register(Registry.FLUID, Lint.id("molten_" + materialName), new MoltenMetalFluid.Still());
			this.flowingFluid = Registry.register(Registry.FLUID, Lint.id("flowing_molten_" + materialName), new MoltenMetalFluid.Flowing());
			stillFluid.flowing = flowingFluid; //Because both fluids need instances of each other
			flowingFluid.still = stillFluid;
		}

		public Item getNuggetItem() {
			return nugget;
		}

		public Item getIngotItem() {
			return ingot;
		}

		public Item getBlockItem() {
			return block;
		}

		public String getMetalName() {
			return materialName;
		}

		public int getColour() {
			return colour;
		}

		public FlowableFluid getStill() {
			return stillFluid;
		}

		public FlowableFluid getFlowing() {
			return flowingFluid;
		}
	}
}
