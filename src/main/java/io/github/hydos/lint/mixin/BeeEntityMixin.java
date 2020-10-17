package io.github.hydos.lint.mixin;

import io.github.hydos.lint.entity.ai.BeeMateGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Flutterer;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeeEntity.class)
public abstract class BeeEntityMixin extends AnimalEntity implements Flutterer {

    protected BeeEntityMixin(EntityType<? extends AnimalEntity> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "initGoals")
    public void initGoals(CallbackInfo ci) {
        this.goalSelector.add(2, new BeeMateGoal(this, 0.001D));
    }
}
