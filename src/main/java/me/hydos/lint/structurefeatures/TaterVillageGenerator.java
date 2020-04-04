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
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.List;
import java.util.Random;

public class TaterVillageGenerator {

    public static Identifier IDENTIFIER = new Identifier("lint", "dungeon_enterance");

    public static void addParts(StructureManager structureManager, BlockPos blockPos, BlockRotation rotation, List<StructurePiece> list, Random random, DefaultFeatureConfig featureConfig){
        list.add(new TaterVillageGenerator.Piece(structureManager, IDENTIFIER, blockPos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {
        private BlockRotation rotation;
        private Identifier template = new Identifier("lint", "dungeon_enterance");

        public Piece(StructureManager structureManager_1, Identifier identifier_1, BlockPos blockPos_1, BlockRotation rotation_1) {
            super(Features.TATER_VILLAGE_PIECE_TYPE, 0);

            this.pos = blockPos_1;
            this.rotation = rotation_1;
            this.template = identifier_1;

            this.setStructureData(structureManager_1);
        }

        public Piece(StructureManager structureManager_1, CompoundTag compoundTag_1) {
            super(Features.TATER_VILLAGE_PIECE_TYPE, compoundTag_1);
            IDENTIFIER = new Identifier(compoundTag_1.getString("Template"));
            this.rotation = BlockRotation.valueOf(compoundTag_1.getString("Rot"));
            this.setStructureData(structureManager_1);
        }

        @Override
        protected void toNbt(CompoundTag compoundTag) {
            super.toNbt(compoundTag);
            System.out.println(this.template.toString() +
                    "\n" +
                    this.rotation.name() +
                    this.pos
            );
            compoundTag.putString("Template", this.template.toString());
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

