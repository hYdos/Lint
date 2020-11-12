package io.github.hydos.lint.client.screen;

import java.util.ArrayList;
import java.util.List;

import io.github.cottonmc.cotton.gui.widget.TooltipBuilder;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.hydos.lint.spirit.HomeSeries;
import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class WSpirit extends WGridPanel {
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	private static final int SIZE = 6;

	private final Identifier id;
	private final List<WSprite> homeSeriesSprites = new ArrayList<>();

	public WSpirit(Identifier id, Spirit spirit) {
		super(18 / SIZE);
		this.setSize(18, 18);

		this.id = id;

		this.add(new WSprite(new Identifier(id.getNamespace(), "textures/spirit/" + id.getPath() + ".png")) {
			@Override
			public void addTooltip(TooltipBuilder tooltip) {
				WSpirit.this.addChildTooltip(tooltip);
			}
		}, 0, 0, SIZE, SIZE);

		HomeSeries homeSeries = spirit.getHomeSeries().get();
		if (homeSeries != null) {
			Registry<HomeSeries> homeSeriesRegistry = CLIENT.player.world.getRegistryManager().get(SpiritRegistry.HOME_SERIES_KEY);
			Identifier homeSeriesId = homeSeriesRegistry.getId(homeSeries);

			WSprite homeSeriesSprite = new WSprite(new Identifier(homeSeriesId.getNamespace(), "textures/home_series/" + homeSeriesId.getPath() + ".png")) {
				@Override
				public void addTooltip(TooltipBuilder tooltip) {
					WSpirit.this.addChildTooltip(tooltip);
				}
			};
			this.homeSeriesSprites.add(homeSeriesSprite);
			this.add(homeSeriesSprite, SIZE - 3, SIZE - 3, 3, 3);
		}
	}

	private void addChildTooltip(TooltipBuilder tooltip) {
		if (CLIENT.options.advancedItemTooltips) {
			tooltip.add(new TranslatableText("spirit." + this.id.getNamespace() + "." + this.id.getPath()));
			tooltip.add(new LiteralText(this.id.toString()).formatted(Formatting.DARK_GRAY));
		}
	}

	public void setHomeSeriesVisible(boolean visible) {
		for (WSprite homeSeriesSprite : this.homeSeriesSprites) {
			if (visible) {
				homeSeriesSprite.setLocation((SIZE - 3) * this.grid, (SIZE - 3 ) * this.grid);
			} else {
				homeSeriesSprite.setLocation(1000 * this.grid, 1000 * this.grid);
			}
		}
	}
}
