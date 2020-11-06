package io.github.hydos.lint.entity.boss.i5;

import io.github.hydos.lint.entity.boss.kingtater.KingTater;
import io.github.hydos.mbb.ModernBossBar;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.text.TextColor;
import net.minecraft.world.World;

public class I509VCB extends HostileEntity implements RangedAttackMob {

    private final ModernBossBar bossBar;

    public I509VCB(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        bossBar = new ModernBossBar(getDisplayName().shallowCopy().styled(style -> style.withBold(true).withColor(TextColor.fromRgb(0xB41917))),0x111E1E1E);
    }

    public static DefaultAttributeContainer.Builder initAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 125)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20);
    }

    @Override
    public void onDeath(DamageSource source) {
        ModernBossBar.instancedBossBars.remove(bossBar);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        target.kill(); //the power of a mod rationer
    }

    @Override
    protected void mobTick() {
        bossBar.setPercent(KingTater.getScaledHealth(getHealth(), getMaxHealth()));
        if (hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 4, true, true, true, null));
        }
        if (hasStatusEffect(StatusEffects.REGENERATION)) {
            addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, true, true, true, null));
        }
    }
}
