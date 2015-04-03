package com.mods.kina.ExperiencePower.event.handler;

import com.mods.kina.ExperiencePower.base.IWrench;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.propsys.ItemStackProperties;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class ClientEventHandler{
    @SubscribeEvent
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        EntityPlayer player = event.player;
        ItemStack itemstack = event.currentItem;
        MovingObjectPosition target = event.target;

        if(player == null) return;
        if(itemstack == null || !(itemstack.getItem() instanceof IWrench)) return;
        if(target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        if(!(player.worldObj.getBlockState(target.getBlockPos()).getBlock() instanceof IWrenchingInfo)) return;

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;

        ((IWrenchingInfo) player.worldObj.getBlockState(target.getBlockPos()).getBlock()).renderInfo(player.worldObj, target.getBlockPos(), d0, d1, d2);

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    @SubscribeEvent
    public void onGetTooltip(ItemTooltipEvent e){
        ItemStackProperties.PropertyContainer prop = ItemStackProperties.instance.getPropertyContainer(e.itemStack);
        if(prop == null) return;
        e.toolTip.add("Weight:" + prop.getWeight());
        e.toolTip.add("Size:" + prop.getSize());
        e.toolTip.add("Hardness:" + prop.getHardness());
        e.toolTip.add("Brittleness:" + prop.getBrittleness());
    }
}
