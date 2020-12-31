package me.hydos.lint.datafixer;

import java.util.Objects;
import java.util.function.Function;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFix;
import com.mojang.datafixers.TypeRewriteRule;
import com.mojang.datafixers.schemas.Schema;
import com.mojang.datafixers.types.Type;
import com.mojang.datafixers.util.Pair;

import net.minecraft.datafixer.TypeReferences;
import net.minecraft.datafixer.fix.ItemNameFix;
import net.minecraft.datafixer.schema.IdentifierNormalizingSchema;

public abstract class DimensionNameFix extends DataFix {
	private final String name;

	public DimensionNameFix(Schema outputSchema, String name) {
		super(outputSchema, false);
		this.name = name;
	}

	public TypeRewriteRule makeRule() {
		Type<Pair<String, String>> type = DSL.named(TypeReferences.LEVEL.typeName(), IdentifierNormalizingSchema.getIdentifierType());
		if (!Objects.equals(this.getInputSchema().getType(TypeReferences.LEVEL), type)) {
			throw new IllegalStateException("level name type is not what was expected.");
		} else {
			return this.fixTypeEverywhere(this.name, type, (dynamicOps) -> {
				return (pair) -> {
					return pair.mapSecond(this::rename);
				};
			});
		}
	}

	protected abstract String rename(String input);

	public static DataFix create(Schema outputSchema, String name, final Function<String, String> rename) {
		return new ItemNameFix(outputSchema, name) {
			protected String rename(String input) {
				return (String)rename.apply(input);
			}
		};
	}
}
