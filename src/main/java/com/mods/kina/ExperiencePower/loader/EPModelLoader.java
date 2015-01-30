package com.mods.kina.ExperiencePower.loader;

import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class EPModelLoader implements ICustomModelLoader{
    public static final EPModelLoader instance = new EPModelLoader();

    public boolean accepts(ResourceLocation modelLocation){
        return false;
    }

    public IModel loadModel(ResourceLocation modelLocation){
        return null;
    }

    public void onResourceManagerReload(IResourceManager p_110549_1_){

    }
}
