package com.mods.kina.ExperiencePower.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerMachineBase extends Container{
    public TileEntityMachineBase machineBase;

    public ContainerMachineBase(World world, IInventory inventory, BlockPos pos){
        machineBase = (TileEntityMachineBase) world.getTileEntity(pos);
        machineBase.initContainer(this);
        int i;
        int j;
        for(i = 0; i < 3; ++i){
            for(j = 0; j < 9; ++j){
                addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i){
            addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 142));
        }
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_){
        return machineBase.isUseableByPlayer(p_75145_1_);
    }

    public Slot addSlotToContainer(Slot slotIn){
        return super.addSlotToContainer(slotIn);
    }
}
