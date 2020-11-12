package io.github.hydos.lint.client.screen;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.WScrollPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.hydos.lint.spirit.Spirit;
import io.github.hydos.lint.spirit.SpiritRegistry;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class SpiritGui extends LightweightGuiDescription {
	private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
	private static final int WIDTH = 10;

	public SpiritGui() {
		WGridPanel root = new WGridPanel();
		this.setRootPanel(root);
		root.setSize(18 * WIDTH, 18 * 6);

		WLabel label = new WLabel(new LiteralText("Spirits"));
		root.add(label, 0, 0, 5, 1);

		Registry<Spirit> spiritRegistry = CLIENT.player.world.getRegistryManager().get(SpiritRegistry.SPIRIT_KEY);
		System.out.println(spiritRegistry.getEntries());
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

			WSprite icon = new WSpirit(id, spirit);
			spiritItemPanel.add(icon, x, y, 1, 1);

			x += 1;
		}
		spiritItemPanel.setSize(18 * WIDTH, 18 * (y + 1));

		WScrollPanel spiritScrollPanel = new WScrollPanel(spiritItemPanel);
		root.add(spiritItemPanel, 0, 1, WIDTH, 5);

		root.validate(this);
	}
}