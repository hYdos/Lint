package me.hydos.lint.entities.boss;

import me.hydos.lint.core.Entities;
import net.minecraft.client.render.entity.feature.SkinOverlayOwner;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class KingTater extends HostileEntity implements SkinOverlayOwner, RangedAttackMob {

    private static final TrackedData<Integer> INVULNERABILITY_TIMER = DataTracker.registerData(KingTater.class, TrackedDataHandlerRegistry.INTEGER);

    private final ServerBossBar bossBar;

    public KingTater(EntityType<? extends KingTater> type, World world) {
        super(type, world);
        moveControl = new KingTaterMoveControl(this);
        bossBar = (ServerBossBar) new ServerBossBar(getDisplayName(), BossBar.Color.PINK, BossBar.Style.PROGRESS).setThickenFog(true).setDarkenSky(true);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new ProjectileAttackGoal(this, 1.0D, 40, 20.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(7, new LookAroundGoal(this));

        this.targetSelector.add(1, new RevengeGoal(this));
        this.targetSelector.add(2, new FollowTargetGoal<>(this, LivingEntity.class, false));
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
    protected void onKilledBy(LivingEntity adversary) {
        if (adversary instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) adversary).networkHandler.sendPacket(new PlaySoundS2CPacket(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, SoundCategory.MASTER, getX(), getY(), getZ(), 1f, 1f));
        }
    }

    @Override
    public void attack(LivingEntity target, float f) {
        TaterMinion minion = new TaterMinion(Entities.MINION, world, target);
        minion.setPos(getX(), getY(), getZ());
        world.spawnEntity(minion);
    }

    @Override
    public void onStartedTrackingBy(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new PlaySoundIdS2CPacket(new Identifier("lint", "music.king_tater"), SoundCategory.HOSTILE, getPos(), 1, 1));
        bossBar.addPlayer(player);
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        player.networkHandler.sendPacket(new StopSoundS2CPacket(new Identifier("lint", "music.king_tater"), SoundCategory.HOSTILE));
        bossBar.removePlayer(player);
    }

    @Override
    protected void mobTick() {
        bossBar.setPercent(getScaledHealth());

        if (hasStatusEffect(StatusEffects.JUMP_BOOST)) {
            addStatusEffect(new StatusEffectInstance(StatusEffects.JUMP_BOOST, 20, 4, true, true, true, null));
        }

        if (hasStatusEffect(StatusEffects.REGENERATION)) {
            addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 20, 0, true, true, true, null));
        }
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

    protected int getTicksUntilNextJump() {
        return this.random.nextInt(20) + 10;
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

    static class KingTaterMoveControl extends MoveControl {

        private float targetYaw;
        private int ticksUntilJump;
        private final KingTater slime;

        public KingTaterMoveControl(KingTater slime) {
            super(slime);
            this.slime = slime;
            this.targetYaw = 180.0F * slime.yaw / 3.1415927F;
        }

        public void tick() {
            this.entity.yaw = this.changeAngle(this.entity.yaw, this.targetYaw, 90.0F);
            this.entity.headYaw = this.entity.yaw;
            this.entity.bodyYaw = this.entity.yaw;

            if (this.state != MoveControl.State.MOVE_TO) {
                this.entity.setForwardSpeed(0.0F);
            } else {
                this.state = MoveControl.State.WAIT;

                if (this.entity.onGround) {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue()));

                    if (this.ticksUntilJump-- <= 0) {
                        this.ticksUntilJump = this.slime.getTicksUntilNextJump();
                        this.slime.getJumpControl().setActive();
                        this.slime.playSound(SoundEvents.ENTITY_SLIME_JUMP, this.slime.getSoundVolume(), ((this.slime.getRandom().nextFloat() - this.slime.getRandom().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                    } else {
                        this.slime.sidewaysSpeed = 0.0F;
                        this.slime.forwardSpeed = 0.0F;
                        this.entity.setMovementSpeed(0.0F);
                    }
                } else {
                    this.entity.setMovementSpeed((float) (this.speed * this.entity.getAttributeInstance(EntityAttributes.MOVEMENT_SPEED).getValue()));
                }
            }
        }
    }
}
