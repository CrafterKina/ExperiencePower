package com.mods.kina.ExperiencePower.slot;

import com.mods.kina.ExperiencePower.recipe.IRecipeWithExp;
import com.mods.kina.ExperiencePower.util.UtilRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotCraftResult extends Slot{
    private final InventoryCrafting craftMatrix;
    private final EntityPlayer thePlayer;
    private int amountCrafted;

    public SlotCraftResult(EntityPlayer player, InventoryCrafting craftingInventory, IInventory p_i45790_3_, int slotIndex, int xPosition, int yPosition){
        super(p_i45790_3_, slotIndex, xPosition, yPosition);
        this.thePlayer = player;
        this.craftMatrix = craftingInventory;
    }

    public boolean isItemValid(ItemStack stack){
        return false;
    }

    public ItemStack decrStackSize(int amount){
        if(this.getHasStack()){
            this.amountCrafted += Math.min(amount, this.getStack().stackSize);
        }

        return super.decrStackSize(amount);
    }

    protected void onCrafting(ItemStack stack, int amount){
        this.amountCrafted += amount;
        this.onCrafting(stack);
    }

    protected void onCrafting(ItemStack stack){
        if(this.amountCrafted > 0){
            stack.onCrafting(this.thePlayer.worldObj, this.thePlayer, this.amountCrafted);
        }

        this.amountCrafted = 0;
    }

    public boolean canTakeStack(EntityPlayer playerIn){
        IRecipeWithExp recipe = UtilRecipe.instance.findMatchingWorkbenchRecipe(craftMatrix, playerIn.worldObj);
        return recipe != null && (playerIn.capabilities.isCreativeMode || playerIn.experienceLevel >= recipe.maxExpLevel()) && recipe.maxExpLevel() > 0 && this.getHasStack();
    }

    public void onPickupFromSlot(EntityPlayer playerIn, ItemStack stack){
        net.minecraftforge.fml.common.FMLCommonHandler.instance().firePlayerCraftingEvent(playerIn, stack, craftMatrix);
        this.onCrafting(stack);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(playerIn);
        ItemStack[] aitemstack = UtilRecipe.instance.getRemainingItemsInWorkbench(this.craftMatrix, playerIn.worldObj);
        net.minecraftforge.common.ForgeHooks.setCraftingPlayer(null);

        IRecipeWithExp recipe = UtilRecipe.instance.findMatchingWorkbenchRecipe(craftMatrix, playerIn.worldObj);
        if(!playerIn.capabilities.isCreativeMode && recipe != null){
            playerIn.addExperienceLevel(-recipe.maxExpLevel());
        }

        for(int i = 0; i < aitemstack.length; ++i){
            ItemStack itemstack1 = this.craftMatrix.getStackInSlot(i);
            ItemStack itemstack2 = aitemstack[i];

            if(itemstack1 != null){
                this.craftMatrix.decrStackSize(i, 1);
            }

            if(itemstack2 != null){
                if(this.craftMatrix.getStackInSlot(i) == null){
                    this.craftMatrix.setInventorySlotContents(i, itemstack2);
                }else if(!this.thePlayer.inventory.addItemStackToInventory(itemstack2)){
                    this.thePlayer.dropPlayerItemWithRandomChoice(itemstack2, false);
                }
            }
        }
    }
}
