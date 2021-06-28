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

package me.hydos.lint.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import me.hydos.lint.item.ArmourMaterials;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ArmorItem.class)
public abstract class ArmorItemMixin {

	@Shadow
	@Final
	private static UUID[] MODIFIERS;
	@Shadow
	@Final
	protected float knockbackResistance;
	@Shadow
	@Final
	@Mutable
	private Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	@Inject(method = "<init>", at = @At(value = "RETURN"))
	private void constructor(ArmorMaterial material, EquipmentSlot slot, Item.Settings settings, CallbackInfo ci) {
		UUID uUID = MODIFIERS[slot.getEntitySlotId()];

		if (material == ArmourMaterials.SICIERON) {
			ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
			this.attributeModifiers.forEach(builder::put);
			builder.put(
					EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE,
					new EntityAttributeModifier(uUID,
							"Armor knockback resistance",
							this.knockbackResistance,
							EntityAttributeModifier.Operation.ADDITION));
			this.attributeModifiers = builder.build();
		}
	}

}