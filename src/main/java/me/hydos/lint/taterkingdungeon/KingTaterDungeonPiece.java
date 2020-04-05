package me.hydos.lint.taterkingdungeon;

import me.hydos.lint.core.Lint;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;

public class KingTaterDungeonPiece extends PoolStructurePiece {

    KingTaterDungeonPiece(StructureManager structureManager_1, StructurePoolElement structurePoolElement_1, BlockPos blockPos_1, int int_1, BlockRotation blockRotation_1, BlockBox mutableIntBoundingBox_1) {
        super(TutorialJigsaws.EXAMPLE_PIECE, structureManager_1, structurePoolElement_1, blockPos_1, int_1, blockRotation_1, mutableIntBoundingBox_1);
    }

    public KingTaterDungeonPiece(StructureManager manager, CompoundTag tag) {
        super(manager, tag, TutorialJigsaws.EXAMPLE_PIECE);
    }
}
