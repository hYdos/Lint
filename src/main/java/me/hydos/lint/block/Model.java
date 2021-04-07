/*
 * Lint
 * Copyright (C) 2020 hYdos, Valoeghese, ramidzkh
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package me.hydos.lint.block;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import me.hydos.lint.Lint;
import net.devtech.arrp.json.blockstate.JBlockModel;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.models.JModel;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.hydos.lint.Lint.RESOURCE_PACK;

// TODO add render layer nonsense here.

/**
 * @reason collect a lot of things in one place and keep them nice and java.
 */
public class Model {
    public Model() {
    }

    StateFunction state = null;
    ModelFunction blockModel = null;
    Boolean opaque = null; // This can also be determined by the material. If both are set, this wins.
    Layer renderLayer = Layer.DEFAULT;
    List<JBlockModel> additionalModels = new ArrayList<>();

    public Model blockState(StateFunction blockStateCreator) {
        this.state = blockStateCreator;
        return this;
    }

    public Model blockModels(ModelFunction modelCreator) {
        this.blockModel = modelCreator;
        return this;
    }

    public Model additionalModels(List<JBlockModel> additionalModels) {
        this.additionalModels = additionalModels;
        return this;
    }

    public Model opaque(boolean opaque) {
        this.opaque = opaque;
        return this;
    }

    public Model renderOn(Layer layer) {
        this.renderLayer = layer;
        return this;
    }

    public Model immutable() {
        return new Immutable(this);
    }

    void createFor(Block block, String id) {
        List<JBlockModel> modelLocations;

        if (this.blockModel == null) {
            modelLocations = Lists.newArrayList(new JBlockModel(Lint.id(id)));
        } else {
            Set<Map.Entry<Identifier, JModel>> models = this.blockModel.createModels(subPath -> Lint.id("block/" + id + (subPath.isEmpty() ? "" : ("_" + subPath)))).entrySet();

            for (Map.Entry<Identifier, JModel> model : models) {
                RESOURCE_PACK.addModel(model.getValue(), model.getKey());
            }

            modelLocations = models.stream().map(entry -> new JBlockModel(entry.getKey())).collect(Collectors.toList()); // Pre-Java 11 support. In java 11, we would use generators.
        }

        // Add additional model locations
        modelLocations.addAll(this.additionalModels);

        if (this.state != null) {
            // "ID Location"
            Identifier idl = Lint.id(id);
            RESOURCE_PACK.addBlockState(this.state.createModel(idl, modelLocations.toArray(new JBlockModel[0])), idl);
        }
    }

    @FunctionalInterface
    public interface StateFunction {
        JState createModel(Identifier id, JBlockModel[] models);
    }

    @FunctionalInterface
    public interface ModelFunction {
        Map<Identifier, JModel> createModels(Function<String, Identifier> ids);
    }

    // States
    private static final StateFunction SIMPLE_STATE = (id, models) -> JState.state(JState.variant().put("", models[0]));

    private static final StateFunction TALL_CROSS_STATE = (id, models) -> JState.state(JState.variant().put("half=lower", models[0]).put("half=upper", models[1]));

    private static final StateFunction SLAB_STATE = (id, models) -> JState.state(JState.variant()
            .put("type=bottom", models[0])
            .put("type=top", models[1])
            .put("type=double", models[2]));

    // Block Model Functions
    private static final ModelFunction CUBE_ALL_MODEL = ids -> {
        Identifier id = ids.apply("");
        return ImmutableMap.of(id, JModel.model().parent("block/cube_all").textures(JModel.textures().var("all", id.toString())));
    };

    private static final ModelFunction CROSS_MODEL = ids -> {
        Identifier id = ids.apply("");
        return ImmutableMap.of(id, JModel.model().parent("block/cross").textures(JModel.textures().var("cross", id.toString())));
    };

    private static final ModelFunction TALL_CROSS_MODEL = ids -> {
        Identifier bottomModelId = ids.apply("");
        Identifier topModelId = ids.apply("top");
        return ImmutableMap.of(
                // bottom model
                bottomModelId,
                JModel.model()
                        .parent("block/cross")
                        .textures(JModel.textures().var("cross", bottomModelId.toString())),
                // top model
                topModelId,
                JModel.model()
                        .parent("block/cross")
                        .textures(JModel.textures().var("cross", topModelId.toString())));
    };

    private static final ModelFunction slabModel(String planksId) {
        return ids -> {
            Identifier lowerSlabIdentifier = ids.apply("");
            Identifier topSlabIdentifier = ids.apply("top");
            Identifier plankModelIdentifier = Lint.id("block/" + planksId);

            return ImmutableMap.of(
                    // bottom model
                    lowerSlabIdentifier,
                    JModel.model()
                            .parent("block/slab")
                            .textures(JModel.textures()
                                    .var("bottom", plankModelIdentifier.toString())
                                    .var("top", plankModelIdentifier.toString())
                                    .var("side", plankModelIdentifier.toString())
                            ),
                    // top model
                    topSlabIdentifier,
                    JModel.model()
                            .parent("block/slab_top")
                            .textures(JModel.textures()
                                    .var("bottom", plankModelIdentifier.toString())
                                    .var("top", plankModelIdentifier.toString())
                                    .var("side", plankModelIdentifier.toString())
                            )
            );
        };
    }

    // Models

    public static final Model NONE = new Model().immutable();

    public static final Model SIMPLE_BLOCKSTATE_ONLY = new Model()
            .blockState(SIMPLE_STATE)
            .immutable();

    public static final Model CUTOUT_SIMPLE_BLOCKSTATE = new Model()
            .blockState(SIMPLE_STATE)
            .opaque(false)
            .renderOn(Layer.CUTOUT_MIPPED)
            .immutable();

    public static final Model SIMPLE_CUBE_ALL = new Model()
            .blockState(SIMPLE_STATE)
            .blockModels(CUBE_ALL_MODEL)
            .immutable();

    public static final Model CUTOUT_CUBE_ALL = new Model()
            .blockState(SIMPLE_STATE)
            .blockModels(CUBE_ALL_MODEL)
            .renderOn(Layer.CUTOUT_MIPPED)
            .opaque(false)
            .immutable();

    public static final Model CROSS = new Model()
            .blockState(SIMPLE_STATE)
            .blockModels(CROSS_MODEL)
            .opaque(false)
            .renderOn(Layer.CUTOUT_MIPPED)
            .immutable();

    public static final Model TALL_PLANT = new Model()
            .blockState(TALL_CROSS_STATE)
            .blockModels(TALL_CROSS_MODEL)
            .opaque(false)
            .renderOn(Layer.CUTOUT_MIPPED)
            .immutable();

    public static final Model slab(String planksId) {
        return new Model()
                .blockState(SLAB_STATE)
                .blockModels(slabModel(planksId))
                .additionalModels(Arrays.asList(new JBlockModel(Lint.id("block/" + planksId))));
    }

    // Immutable Model Class

    private static final class Immutable extends Model {
        public Immutable(Model parent) {
            this.opaque = parent.opaque;
            this.state = parent.state;
            this.blockModel = parent.blockModel;
            this.renderLayer = parent.renderLayer;
            this.additionalModels = parent.additionalModels;
        }

        @Override
        public Model opaque(boolean opaque) {
            throw new UnsupportedOperationException("Cannot set opaque property on an immutable model!");
        }

        @Override
        public Model blockModels(ModelFunction modelCreator) {
            throw new UnsupportedOperationException("Cannot set blockModel property on an immutable model!");
        }

        @Override
        public Model blockState(StateFunction blockStateCreator) {
            throw new UnsupportedOperationException("Cannot set blockState property on an immutable model!");
        }

        @Override
        public Model renderOn(Layer layer) {
            throw new UnsupportedOperationException("Cannot set render layer property on an immutable model!");
        }

        @Override
        public Model additionalModels(List<JBlockModel> additionalModels) {
            throw new UnsupportedOperationException("Cannot set the additional models on an immutable model!");
        }
    }
}
