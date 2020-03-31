package me.hydos.lint.dimensions;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;

public class DimensionManager {

    public static final FabricDimensionType HAYKAM = FabricDimensionType.builder()
            .defaultPlacer((oldEntity, destinationWorld, portalDir, horizontalOffset, verticalOffset) -> new BlockPattern.TeleportTarget(new Vec3d(destinationWorld.getTopPosition(Heightmap.Type.WORLD_SURFACE, BlockPos.ORIGIN)), oldEntity.getVelocity(), (int) oldEntity.yaw))
            .factory(HaykamDimension::new)
            .skyLight(false)
            .buildAndRegister(new Identifier("lint", "haykam"));

    public static void register() {}

}
