package me.hydos.lint.item.materialset;

import me.hydos.lint.util.Power;
import net.minecraft.item.ItemStack;

public interface Enhanceable {
	void update(ItemStack stack, Power.Broad power, float newLevel);
}
