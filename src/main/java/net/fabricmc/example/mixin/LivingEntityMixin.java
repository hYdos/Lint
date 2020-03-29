package net.fabricmc.example.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class LivingEntityMixin extends LivingEntity {


    protected LivingEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "onDeath", cancellable = true)
    public void onDeath(DamageSource source, CallbackInfo ci){
        System.out.println("Ea sports?");
        if(source == DamageSource.OUT_OF_WORLD){
            ci.cancel();
            System.out.println("E");
            super.onDeath(source);
            this.refreshPosition();
        }
    }

    @Shadow
    @Override
    public Iterable<ItemStack> getArmorItems() {
        return null;
    }

    @Shadow
    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return null;
    }

    @Shadow
    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {}

    @Shadow
    @Override
    public Arm getMainArm() {
        return null;
    }
}
