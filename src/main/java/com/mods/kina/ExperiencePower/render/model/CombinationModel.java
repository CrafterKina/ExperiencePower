package com.mods.kina.ExperiencePower.render.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class CombinationModel implements IModel{
    private final List<ModelBlockDefinition.Variant> variants;
    private final List<ResourceLocation> locations = new ArrayList<ResourceLocation>();
    private final List<IModel> models = new ArrayList<IModel>();
    private final IModelState defaultState;

    @SuppressWarnings("unchecked")
    public CombinationModel(ModelBlockDefinition.Variants variants, ModelLoader loader){
        this.variants = variants.getVariants();
        ImmutableMap.Builder<IModelPart,TRSRTransformation> builder = ImmutableMap.builder();
        for(ModelBlockDefinition.Variant v : (List<ModelBlockDefinition.Variant>) variants.getVariants()){
            ResourceLocation loc = v.getModelLocation();
            locations.add(loc);
            IModel model = new PartWrapper(loader.getModel(loc));
            models.add(model);
            builder.put(model, new TRSRTransformation(v.getRotation()));
        }
        defaultState = new MapModelState(builder.build());
    }

    public Collection<ResourceLocation> getDependencies(){
        return ImmutableList.copyOf(locations);
    }

    public Collection<ResourceLocation> getTextures(){
        return Collections.emptyList();
    }

    private IModelState addUV(boolean uv, IModelState state){
        if(uv) return new ModelLoader.UVLock(state);
        return state;
    }

    public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation,TextureAtlasSprite> bakedTextureGetter){
        if(!Attributes.moreSpecific(format, Attributes.DEFAULT_BAKED_FORMAT)){
            throw new IllegalArgumentException("can't bake vanilla weighted models to the format that doesn't fit into the default one: " + format);
        }
        if(variants.size() == 1){
            ModelBlockDefinition.Variant v = variants.get(0);
            IModel model = models.get(0);
            return model.bake(addUV(v.isUvLocked(), state.apply(model)), format, bakedTextureGetter);
        }
        BakedModelBuilder builder = new BakedModelBuilder();
        for(int i = 0; i < variants.size(); i++){
            IModel model = models.get(i);
            ModelBlockDefinition.Variant v = variants.get(i);
            builder.add(model.bake(addUV(v.isUvLocked(), state.apply(model)), format, bakedTextureGetter));
        }
        return new IFlexibleBakedModel.Wrapper(builder.build(), Attributes.DEFAULT_BAKED_FORMAT);
    }

    public IModelState getDefaultState(){
        return defaultState;
    }

    // Weighted models can contain multiple copies of 1 model with different rotations - this is to make it work with IModelState (different copies will be different objects).
    private static class PartWrapper implements IModel{
        private final IModel model;

        public PartWrapper(IModel model){
            this.model = model;
        }

        public Collection<ResourceLocation> getDependencies(){
            return model.getDependencies();
        }

        public Collection<ResourceLocation> getTextures(){
            return model.getTextures();
        }

        public IFlexibleBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation,TextureAtlasSprite> bakedTextureGetter){
            return model.bake(state, format, bakedTextureGetter);
        }

        public IModelState getDefaultState(){
            return model.getDefaultState();
        }
    }

    private static class BakedModelBuilder{
        private List<IFlexibleBakedModel> models;

        public void add(IFlexibleBakedModel model){
            models.add(model);
        }

        public IFlexibleBakedModel build(){
            IFlexibleBakedModel buildedModel = null;
            for(IFlexibleBakedModel model : models){
                if(buildedModel == null){
                    buildedModel = model;
                    continue;
                }
                buildedModel.getGeneralQuads().addAll(model.getGeneralQuads());
                for(EnumFacing facing : EnumFacing.values()){
                    buildedModel.getFaceQuads(facing).addAll(model.getFaceQuads(facing));
                }
            }
            return buildedModel;
        }
    }
}
