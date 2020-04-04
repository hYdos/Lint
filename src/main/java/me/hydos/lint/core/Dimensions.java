package me.hydos.lint.core;

import me.hydos.lint.dimensions.haykam.HaykamDimension;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public interface Dimensions {

    FabricDimensionType HAYKAM = FabricDimensionType.builder()
            .defaultPlacer((oldEntity, destinationWorld, portalDir, horizontalOffset, verticalOffset) -> new BlockPattern.TeleportTarget(new Vec3d(destinationWorld.getTopPosition(Heightmap.Type.WORLD_SURFACE, BlockPos.ORIGIN)), oldEntity.getVelocity(), (int) oldEntity.yaw))
            .factory(HaykamDimension::new)
            .skyLight(true)
            .buildAndRegister(new Identifier("lint", "haykam"));

    static void onInitialize() {
    }
}
