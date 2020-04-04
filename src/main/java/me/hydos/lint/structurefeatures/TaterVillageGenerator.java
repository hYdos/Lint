package me.hydos.lint.structurefeatures;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.List;
import java.util.Random;

public class TaterVillageGenerator {

    public static final Identifier IDENTIFIER = new Identifier("igloo/top");

    public static void addParts(StructureManager structureManager, BlockPos blockPos, BlockRotation rotation, List<StructurePiece> list, Random random, DefaultFeatureConfig featureConfig){

        list.add(new TaterVillageGenerator.Piece(structureManager, IDENTIFIER, blockPos, rotation));

    }

    public static class Piece extends SimpleStructurePiece {
        private BlockRotation rotation;
        private Identifier template;

        public Piece(StructureManager structureManager_1, Identifier identifier_1, BlockPos blockPos_1, BlockRotation rotation_1) {
            super(MyModClass.myStructurePieceType, 0);

            this.pos = blockPos_1;
            this.rotation = rotation_1;
            this.template = identifier_1;

            this.setStructureData(structureManager_1);
        }

        public Piece(StructureManager structureManager_1, CompoundTag compoundTag_1) {
            super(MyModClass.myStructurePieceType, compoundTag_1);
            this.identifier = new Identifier(compoundTag_1.getString("Template"));
            this.rotation = BlockRotation.valueOf(compoundTag_1.getString("Rot"));
            this.setStructureData(structureManager_1);
        }

        @Override
        protected void toNbt(CompoundTag compoundTag_1) {
            super.toNbt(compoundTag_1);
            compoundTag_1.putString("Template", this.template.toString());
            compoundTag_1.putString("Rot", this.rotation.name());
        }

        public void setStructureData(StructureManager structureManager) {
            Structure structure_1 = structureManager.getStructureOrBlank(this.identifier);
            StructurePlacementData structurePlacementData_1 = (new StructurePlacementData()).setRotation(this.rotation).setMirrored(Mirror.NONE).setPosition(pos).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
            this.setStructureData(structure_1, this.pos, structurePlacementData_1);
        }

        @Override
        protected void handleMetadata(String string_1, BlockPos blockPos_1, IWorld iWorld_1, Random random_1, MutableIntBoundingBox mutableIntBoundingBox_1) {

        }

        @Override
        public boolean generate(IWorld iWorld_1, Random random_1, MutableIntBoundingBox mutableIntBoundingBox_1, ChunkPos chunkPos_1) {

        }
    }

}

