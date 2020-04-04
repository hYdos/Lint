package me.hydos.lint.structurefeatures;

import me.hydos.lint.core.Features;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.*;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;

import java.util.List;
import java.util.Random;

public class TaterKingDungeonGenerator {

    public static Identifier IDENTIFIER = new Identifier("lint", "dungeon_enterance");

    public static void addParts(StructureManager structureManager, BlockPos blockPos, BlockRotation rotation, List<StructurePiece> list){
        list.add(new TaterKingDungeonGenerator.Piece(structureManager, IDENTIFIER, blockPos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {
        private BlockRotation rotation;
        public Piece(StructureManager structureManager, Identifier identifier, BlockPos pos, BlockRotation rotation) {
            super(Features.TATER_VILLAGE_PIECE_TYPE, 0);

            this.pos = pos;
            this.rotation = rotation;
            TaterKingDungeonGenerator.IDENTIFIER = identifier;

            this.setStructureData(structureManager);
        }

        public Piece(StructureManager structureManager_1, CompoundTag compoundTag_1) {
            super(Features.TATER_VILLAGE_PIECE_TYPE, compoundTag_1);
            //wtf is this draylarr
            this.rotation = BlockRotation.valueOf(compoundTag_1.getString("Rot"));
            this.setStructureData(structureManager_1);
        }

        @Override
        protected void toNbt(CompoundTag compoundTag) {
            super.toNbt(compoundTag);
            System.out.println(TaterKingDungeonGenerator.IDENTIFIER.toString() +
                    "\n" +
                    this.rotation.name() +
                    this.pos
            );
            compoundTag.putString("Template", TaterKingDungeonGenerator.IDENTIFIER.toString());
            compoundTag.putString("Rot", this.rotation.name());
        }

        public void setStructureData(StructureManager structureManager) {
            Structure structure = structureManager.getStructureOrBlank(IDENTIFIER);
            StructurePlacementData structurePlacementData = (new StructurePlacementData()).setRotation(this.rotation).setMirrored(BlockMirror.NONE).setPosition(pos).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure, this.pos, structurePlacementData);
        }

        @Override
        protected void handleMetadata(String string_1, BlockPos blockPos_1, IWorld iWorld_1, Random random_1, BlockBox mutableIntBoundingBox_1) {

        }
    }

}

