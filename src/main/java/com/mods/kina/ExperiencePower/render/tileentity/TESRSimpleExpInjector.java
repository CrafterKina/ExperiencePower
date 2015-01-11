package com.mods.kina.ExperiencePower.render.tileentity;

import com.mods.kina.ExperiencePower.tileentity.TileEntitySimpleExpInjector;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;

public class TESRSimpleExpInjector extends TileEntitySpecialRenderer{
    public static TESRSimpleExpInjector render = new TESRSimpleExpInjector();
    private EntityItem itemEntity;

    public void renderTileEntityAt(TileEntity var1, double var2, double var4, double var6, float var8, int var9){
        TileEntitySimpleExpInjector injector = (TileEntitySimpleExpInjector) var1;
        ItemStack item = injector.getStackInSlot(0);
        if(item == null){
            //System.out.print(false);
            return;
        }
        GL11.glPushMatrix();
        GL11.glTranslatef((float) var2 + 0.5F, (float) var4 + 0.5F, (float) var6 + 0.5F);
        if(itemEntity == null){
            itemEntity = new EntityItem(null, var2, var4, var6, item);
            itemEntity.getEntityItem().stackSize = 1;
        }else if((itemEntity.getEntityItem() != null) && (!itemEntity.getEntityItem().isItemEqual(item))){
            itemEntity.setEntityItemStack(item.copy());
            itemEntity.getEntityItem().stackSize = 1;
        }
        if(itemEntity.getEntityItem() != null){
            long time = injector.getWorld().getWorldTime();
            if(item.getItem() instanceof ItemBlock){
                GL11.glTranslatef(0.0F, -0.15f + 0.05F * (float) Math.sin(time / 8.0D) + 0.25f, 0.0F);
                GL11.glScaled(1.75, 1.75, 1.75);
                GL11.glRotatef(time / (float) Math.PI, 0.0F, 1.0F, 0.0F);
            }else{
                GL11.glTranslatef(0.0F, -0.2F + 0.05F * (float) Math.sin(time / 8.0D) + 0.5f, 0.0F);
                GL11.glRotatef(time / (float) Math.PI, 0.0F, 1.0F, 0.0F);
            }
            Minecraft.getMinecraft().getRenderManager().renderEntityWithPosYaw(itemEntity, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
        }
        GL11.glPopMatrix();
    }
}
