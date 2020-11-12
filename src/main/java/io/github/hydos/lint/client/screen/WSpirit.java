package io.github.hydos.lint.client.screen;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.hydos.lint.spirit.Spirit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class WSpirit extends WSprite {
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();

	private final Identifier id;

	public WSpirit(Identifier id, Spirit spirit) {
		super(new Identifier(id.getNamespace(), "textures/spirit/" + id.getPath() + ".png"));
		this.id = id;
	}

	@Override
	public void addTooltip(TooltipBuilder tooltip) {
		if (CLIENT.options.advancedItemTooltips) {
			tooltip.add(new TranslatableText("spirit." + this.id.getNamespace() + "." + this.id.getPath()));
			tooltip.add(new LiteralText(this.id.toString()).formatted(Formatting.DARK_GRAY));
		}
	}
}
