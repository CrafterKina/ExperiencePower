package com.mods.kina.ExperiencePower.gui;

import com.mods.kina.ExperiencePower.container.ContainerRotaryMachine;
import com.mods.kina.ExperiencePower.tileentity.TileEntityRotaryMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiRotaryMachine extends GuiContainer{
    private static final ResourceLocation machineGuiTextures = new ResourceLocation("kina_experiencepower:textures/gui/container/rotary_machine.png");
    public TileEntityRotaryMachine tileMachine;

    public GuiRotaryMachine(World world, EntityPlayer player, BlockPos pos){
        super(new ContainerRotaryMachine(world, player, pos));
        this.tileMachine = (TileEntityRotaryMachine) world.getTileEntity(pos);
    }

    /**
     Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_){
        this.fontRendererObj.drawString("RotaryMachine", this.xSize / 2 - this.fontRendererObj.getStringWidth("RotaryMachine") / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(machineGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        if(tileMachine.getField(0) > 0){
            int i1 = getProgressScaled(24);
            this.drawTexturedModalRect(k + 79, l + 34, 176, 14, i1 + 1, 16);
        }
    }

    public int getProgressScaled(int par1){
        return tileMachine.getField(1) != 0 ? tileMachine.getField(0) * par1 / tileMachine.getField(1) : 0;
    }
}
