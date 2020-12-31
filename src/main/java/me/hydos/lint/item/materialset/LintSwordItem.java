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
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
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
import net.minecraft.util.math.MathHelper;
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
			LintEnhancements.enhance(context.getStack(), Power.Broad.THERIA, 1.0f);			
		}

		return super.useOnBlock(context);
	}

	@Override
	public void onAttack(LivingEntity attacker, ItemStack stack, Entity target) {
		World world = target.getEntityWorld();

		// TODO balance this shit.
		// TODO Set bonuses (not modifications to the powers, but modifications to the outcomes themselves due to wearing a full set of armour. Perhaps just keep to armour instead of bleeding to tools)

		if (!world.isClient()) {
			if (target instanceof LivingEntity) {
				LivingEntity le = (LivingEntity) target;

				for (Power.Broad power : LintEnhancements.getEnhancements(stack)) {
					float strength = MathHelper.floor(LintEnhancements.getEnhancement(stack, power)); // integer value as a float from 0 to 12.
					Random rand = world.getRandom();

					switch (power) {
						case ALLOS:
							// SPECIAL: Radiant
							if (rand.nextFloat() * 28.0f < strength) { // 12 levels, so (12/28) = 42.86% is max chance.
								if (rand.nextFloat() * 100.0f < strength) { // 12% * 42.86 = 5.14% chance at level 12. If you want, change the 100.0f to 60.0f for 8.57% chance at level 12 instead.
									LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
									lightning.refreshPositionAfterTeleport(target.getPos());
									lightning.setCosmetic(true);
									le.damage(DamageSource.LIGHTNING_BOLT, Math.min(7, strength * 1.25f)); // radiant damage
									world.spawnEntity(lightning);
								} else {
									le.damage(DamageSource.LIGHTNING_BOLT, 2); // radiant damage
									le.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 120));
								}
							}
							break;
						case MANOS:
							// MAJOR: Toxin
							if (rand.nextFloat() * 24.0f < strength) { // up to 50% chance
								if (rand.nextBoolean() && strength >= 4.0f) { // 50% * 50% = 25%, in best case scenario.
									((LivingEntity) target).addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, (int) ((strength - 3.0f) * 10)));
								}

								((LivingEntity) target).addStatusEffect(new StatusEffectInstance(rand.nextBoolean() ? StatusEffects.SLOWNESS : StatusEffects.BLINDNESS, (int) ((strength + 1.0f) * 10)));
							}

							// SPECIAL: Life Steal
							if (rand.nextFloat() * 100.0f < strength) {
								attacker.heal(0.5f * (float) stack.getAttributeModifiers(EquipmentSlot.MAINHAND).get(EntityAttributes.GENERIC_ATTACK_DAMAGE).stream()
										.filter(mod -> mod.getOperation() == Operation.ADDITION)
										.mapToDouble(mod -> mod.getValue())
										.sum());
							}
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
				Text text = new LiteralText(power.toString().toUpperCase(Locale.ROOT)).formatted(power.formatting).formatted(Formatting.BOLD)
						.append(new LiteralText(" Level " + (int) LintEnhancements.getEnhancement(stack, power)).formatted(Formatting.RESET).formatted(Formatting.WHITE));
				
				tooltip.add(text); // TODO: make translatable
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
			// Major Powers. Can have one up to level 12.
			case ALLOS: // Sword Enhancements: Attack Speed (MAJOR), Damage (MINOR), Radiant (SPECIAL)
				stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier("Weapon modifier", MAJOR_POWER_CONSTANT * 0.5 * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Weapon modifier", MINOR_CONSTANT * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				break;
			case MANOS: // Sword Enhancements: Toxin (MAJOR), Damage (MINOR), Life_Steal (SPECIAL) TODO make nausea have an actual useful effect on lint bosses
				stack.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier("Weapon modifier", MINOR_CONSTANT * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				break;
			// Minor Powers. Can have two up to level 12 each, with a total level cap of 14.
			case THERIA: // Cariar of Mind:		Swiftness_Boost (MAJOR), Paralysis - (can't hit or move) (SPECIAL)
				double multiplier = LintEnhancements.getEnhancement(stack, power) < 6.0 ? 0.011 : 0.008;

				stack.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, new EntityAttributeModifier("Weapon modifier", multiplier * increaseAmount, EntityAttributeModifier.Operation.ADDITION), EquipmentSlot.MAINHAND);
				break;
			case AURIA: // Carien of War:		Damage (MAJOR), Perfect Defense (SPECIAL)
				break;
			case PAWERIA: // Cariar of Order:	Inflict_Weakness (MAJOR) -> chance to inflict weakness. Burst - (on damage taken, you have a chance to halve that damage and let out a burst of energy - you glow for a bit when this happens) (SPECIAL)
				break;
			case HERIA: // Carien of Emotion:	Passive Regeneration (MAJOR) -> natural regen treats hunger value as higher? or some other mechanism, Adrenaline (SPECIAL) - (the first time you take damage, you gain a surge of strength, regen, and swiftenss - resets 10 mins after)
				break;
			default:
				break;
		}
	}

	// Nice simple constants for where this shit works nicely: basically the proportions for if the ability is featured multiple times.
	// However if the ability is only featured once these aren't really necessary
	public static final double MAJOR_POWER_CONSTANT = 1.0;
	public static final double MAJOR_CARIA_CONSTANT = 0.8;
	public static final double MINOR_CONSTANT = 0.5;
}
