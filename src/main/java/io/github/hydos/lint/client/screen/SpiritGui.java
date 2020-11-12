package io.github.hydos.lint.client.screen;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WToggleButton;
import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class SpiritGui extends LightweightGuiDescription {
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	private static final int WIDTH = 12;
	private static final int HEIGHT = 7;

	private final List<WSpirit> icons = new ArrayList<>();

	public SpiritGui() {
		WGridPanel root = new WGridPanel();
		this.setRootPanel(root);
		root.setSize(18 * WIDTH, 18 * HEIGHT);

		WLabel label = new WLabel(new TranslatableText("text.lint.screen.spirits.title"));
		root.add(label, 0, 0, WIDTH, 1);

		WToggleButton showHomeSeriesToggle = new WToggleButton(new TranslatableText("text.lint.screen.spirits.show_home_series"));
		root.add(showHomeSeriesToggle, 4, 0, WIDTH - 4, 1);
		showHomeSeriesToggle.setToggle(true);
		showHomeSeriesToggle.setOnToggle(toggled -> {
			for (WSpirit icon : this.icons) {
				icon.setHomeSeriesVisible(toggled);
			}
		});

		Registry<Spirit> spiritRegistry = CLIENT.player.world.getRegistryManager().get(SpiritRegistry.SPIRIT_KEY);
		List<Map.Entry<RegistryKey<Spirit>, Spirit>> spirits = spiritRegistry.getEntries().stream().sorted((first, second) -> {
			return first.getKey().getValue().compareTo(second.getKey().getValue());
		}).collect(Collectors.toList());

		WGridPanel spiritItemPanel = new WGridPanel();
		int x = 0;
		int y = 0;
		for (Map.Entry<RegistryKey<Spirit>, Spirit> entry : spirits) {
			Identifier id = entry.getKey().getValue();
			Spirit spirit = entry.getValue();

			if (x > WIDTH) {
				x = 0;
				y += 1;
			}

			WSpirit icon = new WSpirit(id, spirit);
			this.icons.add(icon);
			spiritItemPanel.add(icon, x, y, 1, 1);

			x += 1;
		}
		spiritItemPanel.setSize(18 * WIDTH, 18 * (y + 1));

		WScrollPanel spiritScrollPanel = new WScrollPanel(spiritItemPanel);
		root.add(spiritItemPanel, 0, 1, WIDTH, HEIGHT - 1);

		root.validate(this);
	}
}