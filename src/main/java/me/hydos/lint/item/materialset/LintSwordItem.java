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

package me.hydos.lint.item.materialset;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.hydos.enhancement.Enhanceable;
import me.hydos.enhancement.LintEnhancements;
import me.hydos.lint.util.Power;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemStack.TooltipSection;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class LintSwordItem extends SwordItem implements Enhanceable {
	public LintSwordItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
		this.attackSpeed = attackSpeed;
	}

	private final float attackSpeed;

	public float getAttackSpeed() {
		return this.attackSpeed;
	}

	@Override
	public ActionResult useOnBlock(ItemUsageContext context) {
		if (!context.getWorld().isClient()) {
			LintEnhancements.enhance(context.getStack(), Power.Broad.ALLOS, 1.0f);			
		}

		return super.useOnBlock(context);
	}

	@Override
	public void onAttack(ItemStack stack, Entity target) {
		World world = target.getEntityWorld();

		if (!world.isClient()) {
			if (target instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) target;

				for (Power.Broad power : LintEnhancements.getEnhancements(stack)) {
					float strength = LintEnhancements.getEnhancement(stack, power);
					Random rand = world.getRandom();

					switch (power) {
						case ALLOS:
							if (rand.nextBoolean() && rand.nextFloat() * 14.0f < strength) { // 12 levels, so 50% * 6/7 = 42.86% is max chance.
								if (rand.nextFloat() * 100.0f < strength) { // 12% * 42.86 = 5.14% chance at level 12. Change the 100.0f to 60.0f for 8.57 chance at level 12 instead.
									LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
									lightning.refreshPositionAfterTeleport(target.getPos());
									lightning.setCosmetic(true);
									le.damage(DamageSource.LIGHTNING_BOLT, 7); // radiant damage
									world.spawnEntity(lightning);
								} else {
									le.damage(DamageSource.LIGHTNING_BOLT, 2); // radiant damage
									le.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 120));
								}
							}
							break;
						case MANOS:
							
							break;
						default:
							break;
					}
				}
			}
		}
	}

	@Override
	public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
		Collection<Power.Broad> powers = LintEnhancements.getEnhancements(stack);

		if (!powers.isEmpty()) {
			for (Power.Broad power : powers) {
				tooltip.add(
						new LiteralText(power.toString().toUpperCase(Locale.ROOT)).formatted(power.formatting).formatted(Formatting.BOLD)
						.append(new LiteralText(" Level " + (int) LintEnhancements.getEnhancement(stack, power) + "\n"))); // TODO: make translatable
			}
		}
	}

	@Override
	public void update(ItemStack stack, Power.Broad power, float increaseAmount, boolean addDefaults) {
		if (addDefaults) {
			stack.addHideFlag(TooltipSection.MODIFIERS);
			stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Weapon modifier", this.getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
			stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Weapon modifier", this.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
		}

		// I, valoeghese, will write this
		// Major powers have MAJOR, MINOR and SPECIAL enhancements (which all increase in proficiency as you level up)
		// Minor powers have only their MAJOR enhancements (but it's somewhere between a major power's major and minor level) and a SPECIAL once you reach the max level.
		switch (power) {
			case ALLOS: // Sword Enhancements: Speed (MAJOR), Damage (MINOR), Radiant (SPECIAL)
				stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Weapon modifier", MAJOR_POWER_CONSTANT * ATTACK_SPEED_MOD_CONSTANT * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Weapon modifier", MINOR_CONSTANT * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				break;
			case MANOS: // Sword Enhancements: Toxin (MAJOR), Damage (MINOR), Life_Steal (SPECIAL) TODO make nausea have an actual useful effect on lint bosses
				stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Weapon modifier", MINOR_CONSTANT * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				break;
			case THERIA:
				break;
			case AURIA:
				break;
			case PAWERIA:
				break;
			case HERIA:
				break;
			default:
				break;
		}
	}

	public static final double MAJOR_POWER_CONSTANT = 1.0;
	public static final double MAJOR_CARIA_CONSTANT = 0.8;
	public static final double MINOR_CONSTANT = 0.5;

	private static final double ATTACK_SPEED_MOD_CONSTANT = 0.5;
}
