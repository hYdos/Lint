package io.github.hydos.lint.entity.boss.i5;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TextColor;
import net.minecraft.world.World;

public class I509VCB extends HostileEntity implements RangedAttackMob {

    private final ServerBossBar bossBar;

    public I509VCB(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
        bossBar = (ServerBossBar) new ServerBossBar(getDisplayName().shallowCopy().styled(style -> style.withBold(true).withColor(TextColor.fromRgb(0xB41917))), BossBar.Color.YELLOW, BossBar.Style.PROGRESS).setThickenFog(true).setDarkenSky(true);
    }

    public static DefaultAttributeContainer.Builder initAttributes() {
        return LivingEntity.createLivingAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 125)
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        target.kill(); //the power of a mod rationer
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        bossBar.removePlayer(player);
    }
}
