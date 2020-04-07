package me.hydos.lint.entity.ai;

import net.minecraft.advancement.criterion.Criterions;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;
import java.util.Random;

public class BeeMateGoal extends Goal {

    private static final TargetPredicate VALID_MATE_PREDICATE = (new TargetPredicate()).setBaseMaxDistance(8.0D).includeInvulnerable().includeTeammates().includeHidden();
    protected final AnimalEntity animal;
    protected final World world;
    private final Class<? extends AnimalEntity> entityClass;
    private final double chance;
    private final Random random;
    protected AnimalEntity mate;
    private int timer;

    public BeeMateGoal(AnimalEntity animal, double chance) {
        this(animal, chance, animal.getClass());
    }

    public BeeMateGoal(AnimalEntity animal, double chance, Class<? extends AnimalEntity> entityClass) {
        this.random = new Random(56496530926L);
        this.animal = animal;
        this.world = animal.world;
        this.entityClass = entityClass;
        this.chance = chance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    public boolean canStart() {
        this.mate = this.findMate();
        int rand = random.nextInt(600);
        return this.mate != null && rand == 4;
    }

    public boolean shouldContinue() {
        return this.mate.isAlive() && this.timer < 60;
    }

    public void stop() {
        this.mate = null;
        this.timer = 0;
    }

    public void tick() {
        this.animal.getLookControl().lookAt(this.mate, 10.0F, (float) this.animal.getLookPitchSpeed());
        this.animal.getNavigation().startMovingTo(this.mate, this.chance);
        ++this.timer;
        if (this.timer >= 60 && this.animal.squaredDistanceTo(this.mate) < 9.0D) {
            this.breed();
        }
    }

    private AnimalEntity findMate() {
        List<AnimalEntity> list = this.world.getTargets(this.entityClass, VALID_MATE_PREDICATE, this.animal, this.animal.getBoundingBox().expand(8.0D));
        double d = Double.MAX_VALUE;
        AnimalEntity animalEntity = null;

        for (AnimalEntity animalEntity2 : list) {
            if (this.animal.squaredDistanceTo(animalEntity2) < d) {
                animalEntity = animalEntity2;
                d = this.animal.squaredDistanceTo(animalEntity2);
            }
        }
        //just in case it gets out of hand
        if (list.size() > 120) {
            return null;
        }
        return animalEntity;
    }

    protected void breed() {
        PassiveEntity passiveEntity = this.animal.createChild(this.mate);
        if (passiveEntity != null) {
            ServerPlayerEntity serverPlayerEntity = this.animal.getLovingPlayer();
            if (serverPlayerEntity == null && this.mate.getLovingPlayer() != null) {
                serverPlayerEntity = this.mate.getLovingPlayer();
            }

            if (serverPlayerEntity != null) {
                serverPlayerEntity.incrementStat(Stats.ANIMALS_BRED);
                Criterions.BRED_ANIMALS.trigger(serverPlayerEntity, this.animal, this.mate, passiveEntity);
            }

            this.animal.setBreedingAge(6000);
            this.mate.setBreedingAge(6000);
            this.animal.resetLoveTicks();
            this.mate.resetLoveTicks();
            passiveEntity.setBreedingAge(-24000);
            passiveEntity.refreshPositionAndAngles(this.animal.getX(), this.animal.getY(), this.animal.getZ(), 0.0F, 0.0F);
            this.world.spawnEntity(passiveEntity);
            this.world.sendEntityStatus(this.animal, (byte) 18);

            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.spawnEntity(new ExperienceOrbEntity(this.world, this.animal.getX(), this.animal.getY(), this.animal.getZ(), this.animal.getRandom().nextInt(7) + 1));
            }
        }
    }
}
