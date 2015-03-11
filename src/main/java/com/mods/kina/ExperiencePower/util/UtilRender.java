package com.mods.kina.ExperiencePower.util;

import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;

public class UtilRender{
    public static final UtilRender instance = new UtilRender();

    private static final Tessellator tessellator = Tessellator.getInstance();
    private static final WorldRenderer worldRenderer = tessellator.getWorldRenderer();

    @Deprecated
    public void drawOutlinedBoundingBox(AxisAlignedBB aabb){
        RenderGlobal.drawOutlinedBoundingBox(aabb, -1);
    }
}
