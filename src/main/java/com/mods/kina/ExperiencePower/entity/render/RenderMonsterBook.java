package com.mods.kina.ExperiencePower.entity.render;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMonsterBook extends RenderLiving{
    //private ModelBook model=new ModelBook();
    public RenderMonsterBook(RenderManager manager){
        super(manager, new ModelBook(), 0);
        shadowSize = 0;
    }

    /**
     Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity){
        return new ResourceLocation("textures/entity/enchanting_table_book.png");
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
     {
     //this.func_177067_a(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_);
     model.render(p_76986_1_,0,0,0,0,0,0);
     }

     *//**
     * Renders the entity's shadow and fire (if its on fire). Args: entity, x, y, z, yaw, partialTickTime
     *//*
    public void doRenderShadowAndFire(Entity p_76979_1_, double p_76979_2_, double p_76979_4_, double p_76979_6_, float p_76979_8_, float p_76979_9_){
        model.render(p_76979_1_,0,0,0,0,0,0);
    }*/
}
