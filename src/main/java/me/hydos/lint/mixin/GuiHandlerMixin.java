package me.hydos.lint.mixin;

import me.hydos.lint.blocks.AdventureTransformerGui;
import me.hydos.techrebornApi.TechRebornApi;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.gui.screen.ingame.ContainerScreen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import techreborn.blockentity.machine.multiblock.IndustrialGrinderBlockEntity;
import techreborn.blockentity.machine.tier1.AssemblingMachineBlockEntity;
import techreborn.client.EGui;
import techreborn.client.GuiHandler;
import techreborn.client.gui.GuiAssemblingMachine;
import techreborn.client.gui.GuiIndustrialGrinder;

@Mixin(value = GuiHandler.class, remap = false)
public class GuiHandlerMixin {



    @Inject(at = @At("HEAD"), method = "getClientGuiElement", cancellable = true)
    private static void e(EGui gui, PlayerEntity player, BlockPos pos, int syncID, CallbackInfoReturnable<ContainerScreen<?>> cir){
        if(gui == EGui.valueOf("mcp")){
            BlockEntity blockEntity = player.world.getBlockEntity(pos);
            cir.setReturnValue(new AdventureTransformerGui(syncID, player, (IndustrialGrinderBlockEntity) blockEntity));
        }
    }

}
