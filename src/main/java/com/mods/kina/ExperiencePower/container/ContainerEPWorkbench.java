package com.mods.kina.ExperiencePower.container;

import com.mods.kina.ExperiencePower.recipe.IRecipeWithExp;
import com.mods.kina.ExperiencePower.slot.SlotCraftResult;
import com.mods.kina.ExperiencePower.util.UtilRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerEPWorkbench extends Container{
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
    public IInventory craftResult = new InventoryCraftResult();
    private World worldObj;
    private BlockPos pos;

    public ContainerEPWorkbench(World worldIn, EntityPlayer player, BlockPos pos){
        this.worldObj = worldIn;
        this.pos = pos;
        addSlotToContainer(new SlotCraftResult(player, craftMatrix, craftResult, 0, 124, 35));
        int i;
        int j;

        for(i = 0; i < 3; ++i){
            for(j = 0; j < 3; ++j){
                addSlotToContainer(new Slot(craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
            }
        }

        for(i = 0; i < 3; ++i){
            for(j = 0; j < 9; ++j){
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(i = 0; i < 9; ++i){
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
        onCraftMatrixChanged(craftMatrix);
    }

    public void onCraftMatrixChanged(IInventory inventoryIn){
        IRecipeWithExp recipe = UtilRecipe.instance.findMatchingWorkbenchRecipe(craftMatrix, worldObj);
        ItemStack result = recipe == null ? null : recipe.getCraftingResult(craftMatrix);
        craftResult.setInventorySlotContents(0, result);
    }

    public void onContainerClosed(EntityPlayer playerIn){
        super.onContainerClosed(playerIn);

        if(!worldObj.isRemote){
            for(int i = 0; i < 9; ++i){
                ItemStack itemstack = craftMatrix.getStackInSlotOnClosing(i);

                if(itemstack != null){
                    playerIn.dropPlayerItemWithRandomChoice(itemstack, false);
                }
            }
        }
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return playerIn.getDistanceSq((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D) <= 64.0D;
    }

    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        ItemStack itemstack = null;
        Slot slot = (Slot) inventorySlots.get(index);

        if(slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index == 0){
                if(!mergeItemStack(itemstack1, 10, 46, true)){
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }else if(index >= 10 && index < 37){
                if(!mergeItemStack(itemstack1, 37, 46, false)){
                    return null;
                }
            }else if(index >= 37 && index < 46){
                if(!mergeItemStack(itemstack1, 10, 37, false)){
                    return null;
                }
            }else if(!mergeItemStack(itemstack1, 10, 46, false)){
                return null;
            }

            if(itemstack1.stackSize == 0){
                slot.putStack(null);
            }else{
                slot.onSlotChanged();
            }

            if(itemstack1.stackSize == itemstack.stackSize){
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }

    public boolean canMergeSlot(ItemStack p_94530_1_, Slot p_94530_2_){
        return p_94530_2_.inventory != craftResult && super.canMergeSlot(p_94530_1_, p_94530_2_);
    }
}
