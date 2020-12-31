/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

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
