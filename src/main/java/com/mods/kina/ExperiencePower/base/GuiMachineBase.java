package com.mods.kina.ExperiencePower.base;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiMachineBase extends GuiContainer{
    protected ResourceLocation guiTex;

    public GuiMachineBase(World world, EntityPlayer player, BlockPos pos){
        super(new ContainerMachineBase(world, player, pos));
        //guiTex = new ResourceLocation(StaticFieldCollection.MODID, "textures/gui/container/" + machineBase.machineBase.getWorld().getBlockState(machineBase.machineBase.getPos()).getBlock().getUnlocalizedName().substring(5) + ".png");
        guiTex = new ResourceLocation("textures/gui/container/dispenser.png");
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int mX, int mY){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(guiTex);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }

    protected void drawGuiContainerForegroundLayer(int mX, int mY){
        fontRendererObj.drawString(((ContainerMachineBase) inventorySlots).machineBase.getDisplayName().getFormattedText(), this.xSize / 2 - this.fontRendererObj.getStringWidth(((ContainerMachineBase) inventorySlots).machineBase.getDisplayName().getFormattedText()) / 2, 6, 4210752);
        fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }
}
