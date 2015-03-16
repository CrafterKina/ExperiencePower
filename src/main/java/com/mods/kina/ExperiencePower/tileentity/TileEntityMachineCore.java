package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityEPBase;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import java.util.Arrays;

public class TileEntityMachineCore extends TileEntityEPBase implements ISidedInventory{
    public EnumDyeColor[] colors = new EnumDyeColor[6];

    public TileEntityMachineCore(){
        super("machine_core", false, 16);
        Arrays.fill(colors, EnumDyeColor.WHITE);
    }

    protected void writeToNBTExtended(NBTTagCompound compound){
        for(EnumFacing f : EnumFacing.values()){
            compound.setByte(f.getName(), (byte) colors[f.getIndex()].ordinal());
        }
    }

    protected void readFromNBTExtended(NBTTagCompound compound){
        for(EnumFacing f : EnumFacing.values()){
            colors[f.getIndex()] = EnumDyeColor.values()[compound.getByte(f.getName())];
        }
    }

    public int[] getSlotsForFace(EnumFacing side){
        return new int[]{colors[side.getIndex()].ordinal()};
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return isItemValidForSlot(index, itemStackIn);
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        return true;
    }
}
