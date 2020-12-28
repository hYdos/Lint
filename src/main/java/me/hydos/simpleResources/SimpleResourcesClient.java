package me.hydos.simpleResources;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.minecraft.util.Identifier;

public class SimpleResourcesClient {

	public static RuntimeResourcePack createResourcePack(String modid, int version) {
		return RuntimeResourcePack.create(modid, version);
	}

	public static JState createBlockState(Identifier modelIdentifier) {
		return JState.state(
				JState.variant(
						JState.model(
								modelIdentifier.toString())));
	}

	public static JModel createBlockItemModel(Identifier blockModelIdentifier) {
		return JModel.model(blockModelIdentifier.toString());
	}

	public static JModel createBlockModel(Identifier texture) {
		return JModel
				.model("block/cube_all")
				.textures(new JTextures()
						.var("all", texture.toString()));
	}
}
