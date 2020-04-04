package me.hydos.lint.entities.boss;

import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class KingTater extends HostileEntity implements SkinOverlayOwner, RangedAttackMob {

    private static final TrackedData<Integer> INVULNERABILITY_TIMER = DataTracker.registerData(KingTater.class, TrackedDataHandlerRegistry.INTEGER);

    private final ServerBossBar bossBar;

    public KingTater(EntityType<? extends KingTater> type, World world) {
        super(type, world);
        bossBar = (ServerBossBar) new ServerBossBar(getDisplayName(), BossBar.Color.PINK, BossBar.Style.PROGRESS).setThickenFog(true).setDarkenSky(true);
    }

    @Override
    protected void initGoals() {
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        dataTracker.startTracking(INVULNERABILITY_TIMER, 0);
    }

    @Override
    protected void initAttributes() {
        super.initAttributes();
        getAttributeInstance(EntityAttributes.MAX_HEALTH).setBaseValue(300.0D);
    }

    @Override
    public void attack(LivingEntity target, float f) {
        // TODO: Summon tater minion
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
    protected void mobTick() {
        bossBar.setPercent(getScaledHealth());
    }

    @Override
    public void writeCustomDataToTag(CompoundTag tag) {
        super.writeCustomDataToTag(tag);
        tag.putInt("InvulnerabilityTicks", getInvulnerableTicks());
    }

    @Override
    public void readCustomDataFromTag(CompoundTag tag) {
        super.readCustomDataFromTag(tag);
        setInvulnerabilityTicks(tag.getInt("InvulnerabilityTicks"));

        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
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
        return getType().getDimensions().scaled(getScaledHealth());
    }

    @Override
    public boolean shouldRenderOverlay() {
        return getScaledHealth() <= 0.5D;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    public int getInvulnerableTicks() {
        return this.dataTracker.get(INVULNERABILITY_TIMER);
    }

    public void setInvulnerabilityTicks(int ticks) {
        this.dataTracker.set(INVULNERABILITY_TIMER, ticks);
    }

    public float getScaledHealth() {
        return getHealth() / getMaximumHealth();
    }
}
