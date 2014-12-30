package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class GuiMachineBase extends GuiContainer{
    protected ResourceLocation guiTex;

    public GuiMachineBase(Container p_i1072_1_, String texName){
        super(p_i1072_1_);
        guiTex = new ResourceLocation(StaticFieldCollection.MODID, "textures/gui/container/" + texName + ".png");
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
