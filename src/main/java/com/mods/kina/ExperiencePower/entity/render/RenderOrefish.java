package com.mods.kina.ExperiencePower.entity.render;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderOrefish extends RenderLiving{
    private static final ResourceLocation silverfishTextures = new ResourceLocation("textures/entity/silverfish.png");

    public RenderOrefish(RenderManager p_i46144_1_){
        super(p_i46144_1_, new ModelSilverfish(), 0.3F);
    }

    protected float getDeathMaxRotation(EntityLivingBase p_77037_1_){
        return 180.0F;
    }

    /**
     Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity entity){
        return silverfishTextures;
    }
}
