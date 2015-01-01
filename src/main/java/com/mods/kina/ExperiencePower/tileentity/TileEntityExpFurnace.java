package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public class TileEntityExpFurnace extends TileEntityMachineBase implements ISidedInventory{
    public TileEntityExpFurnace(){
        super("exp_furnace", false, 3);
    }

    /**
     Containerにスロットを追加したりする。 やろうと思えばなんでもできるが多分スロット追加だけ。

     @param container
     */
    public void initContainer(ContainerMachineBase container){

    }

    public int[] getSlotsForFace(EnumFacing side){
        return new int[0];
    }

    /**
     Returns true if automation can insert the given item in the given slot from the given side. Args: slot, item, side

     @param index
     @param itemStackIn
     @param direction
     */
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return false;
    }

    /**
     Returns true if automation can extract the given item in the given slot from the given side. Args: slot, item, side

     @param index
     @param stack
     @param direction
     */
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return false;
    }
}
