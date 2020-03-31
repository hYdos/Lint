package me.hydos.lint.entities.liltaterBattery;

import me.hydos.lint.Lint;
import net.minecraft.entity.ai.goal.FollowOwnerGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

public class LilTaterBattery extends TameableEntity {

    public LilTaterBattery(World world){
        super(Lint.LIL_TATER, world);

    }

    @Override
    protected void initGoals() {
        super.initGoals();
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 10));
        this.goalSelector.add(1, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F, false));
    }

    @Override
    public PassiveEntity createChild(PassiveEntity mate) {
        return this;
    }
}
