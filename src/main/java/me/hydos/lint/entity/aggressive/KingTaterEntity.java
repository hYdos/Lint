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

package me.hydos.lint.entity.aggressive;

import me.hydos.lint.entity.Entities;
import me.hydos.lint.item.LintItems;
import me.hydos.lint.sound.Sounds;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.function.Predicate;

@SuppressWarnings("EntityConstructor")
public class KingTaterEntity extends HostileEntity implements RangedAttackMob {

	private static final Predicate<LivingEntity> PREDICATE = entity -> entity.getType() != Entities.MINION;

	private final Set<UUID> minions = new HashSet<>();
	private final ServerBossBar bossBar;

	public KingTaterEntity(EntityType<? extends KingTaterEntity> type, World world) {
		super(type, world);
		bossBar = (ServerBossBar) new ServerBossBar(getDisplayName(), BossBar.Color.GREEN, BossBar.Style.PROGRESS).setThickenFog(true).setDarkenSky(true);
	}

	public static DefaultAttributeContainer.Builder createAttributes() {
		return LivingEntity.createLivingAttributes()
				.add(EntityAttributes.GENERIC_MAX_HEALTH, 300)
				.add(EntityAttributes.GENERIC_FOLLOW_RANGE, 6)
				.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 15);
	}

	public static float getScaledHealth(float health, float maxHealth) {
		return health / maxHealth;
	}

	@Override
	protected void initGoals() {
		this.goalSelector.add(1, new SwimGoal(this));
		this.goalSelector.add(2, new ProjectileAttackGoal(this, 1.0D, 40, 40.0F));
		this.goalSelector.add(3, new MeleeAttackGoal(this, 1D, false));
		this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
		this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
		this.goalSelector.add(7, new LookAroundGoal(this));

		this.targetSelector.add(1, new RevengeGoal(this));
		this.targetSelector.add(2, new FollowTargetGoal<>(this, PlayerEntity.class, false));
		this.targetSelector.add(4, new FollowTargetGoal<>(this, LivingEntity.class, 10, false, false, PREDICATE));
	}

	@Override
	protected void onKilledBy(LivingEntity killer) {
		if (killer != null) {
			killer.dropStack(new ItemStack(LintItems.TATER_ESSENCE));
			if (killer instanceof ServerPlayerEntity) {
				((ServerPlayerEntity) killer).networkHandler.sendPacket(new PlaySoundS2CPacket(Sounds.ADVANCEMENT, SoundCategory.MASTER, getX(), getY(), getZ(), 1f, 1f));
			}
		}
	}

	@Override
	public void attack(LivingEntity target, float f) {
		TaterMinionEntity minion = new TaterMinionEntity(Entities.MINION, world, target);
		minion.refreshPositionAndAngles(getX(), getY(), getZ(), 0, 0);
		minions.add(minion.getUuid());
		world.spawnEntity(minion);
	}

	@Override
	public void onStartedTrackingBy(ServerPlayerEntity player) {
		bossBar.addPlayer(player);
	}

	@Override
	public void onStoppedTrackingBy(ServerPlayerEntity player) {
		bossBar.removePlayer(player);
	}

	@Override
	public void remove(RemovalReason reason) {
		super.remove(reason);

		for (UUID id : minions) {
			Entity minion = ((ServerWorld) world).getEntity(id);

			if (minion != null) {
				minion.remove(reason);
			}
		}
	}

	@Override
	protected void mobTick() {
		bossBar.setPercent(getScaledHealth(getHealth(), getMaxHealth()));
		if (hasStatusEffect(StatusEffects.JUMP_BOOST)) {
			addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 4, true, true, true, null));
		}
		if (hasStatusEffect(StatusEffects.REGENERATION)) {
			addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, true, true, true, null));
		}
	}

	@Override
	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);

		if (this.hasCustomName()) {
			this.bossBar.setName(this.getDisplayName());
		}

		NbtElement t = tag.get("Minions");
		minions.clear();

		if (t instanceof NbtList) {
			for (NbtElement id : ((NbtList) t)) {
				minions.add(UUID.fromString(id.asString()));
			}
		}
	}

	@Override
	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		NbtList list = new NbtList();
		tag.put("Minions", list);

		for (UUID minion : minions) {
			list.add(NbtString.of(minion.toString()));
		}
	}

	@Override
	public void setCustomName(Text name) {
		super.setCustomName(name);
		this.bossBar.setName(this.getDisplayName());
	}

	@Override
	protected boolean canStartRiding(Entity entity) {
		return false;
	}

	@Override
	public EntityDimensions getDimensions(EntityPose pose) {
		return getType().getDimensions().scaled(getScaledHealth(getHealth(), getMaxHealth()));
	}

	@Override
	public boolean canUsePortals() {
		return false;
	}
}
