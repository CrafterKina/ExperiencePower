package com.mods.kina.ExperiencePower.entity.render;

import net.minecraft.client.model.ModelBook;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderMonsterBook extends RenderLiving{
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
}
