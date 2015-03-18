package com.mods.kina.ExperiencePower.gui;

import com.mods.kina.ExperiencePower.container.ContainerEPWorkbench;
import com.mods.kina.ExperiencePower.recipe.IRecipeWithExp;
import com.mods.kina.ExperiencePower.util.UtilRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiEPWorkbench extends GuiContainer{
    private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("textures/gui/container/crafting_table.png");
    private final World world;
    private final EntityPlayer player;

    public GuiEPWorkbench(World world, EntityPlayer player, BlockPos pos){
        super(new ContainerEPWorkbench(world, player, pos));
        this.world = world;
        this.player = player;
    }

    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
        this.fontRendererObj.drawString(I18n.format("container.crafting"), 28, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
        IRecipeWithExp recipe = UtilRecipe.instance.findMatchingWorkbenchRecipe(((ContainerEPWorkbench) inventorySlots).craftMatrix, world);
        if(recipe != null){
            int k = fontRendererObj.getColorCode(EnumChatFormatting.DARK_GREEN.toString().charAt(1));
            String s = I18n.format(EnumChatFormatting.BOLD + "Cost: %1$d", recipe.maxExpLevel());

            if(!inventorySlots.getSlot(0).canTakeStack(player)){
                k = fontRendererObj.getColorCode(EnumChatFormatting.DARK_RED.toString().charAt(1));
            }

            int l = -16777216 | (k & 16579836) >> 2 | k & -16777216;
            int i1 = this.xSize - 8 - 8 - 8 - 4 - this.fontRendererObj.getStringWidth(s);
            byte b0 = 67 - 8;
            this.fontRendererObj.drawString(s, i1, b0, k);
        }

    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
    }
}
