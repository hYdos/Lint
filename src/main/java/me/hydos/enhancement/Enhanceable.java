package me.hydos.enhancement;

import me.hydos.lint.util.Power;
import net.minecraft.item.ItemStack;

public interface Enhanceable {
	void update(ItemStack stack, Power.Broad power, float change);
}
