package com.mods.kina.ExperiencePower.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ContainerMachineBase extends Container{
    public TileEntityMachineBase machineBase;
    public IInventory playerInventory;

    public ContainerMachineBase(EntityPlayer player, World world, int x, int y, int z){
        this.machineBase = (TileEntityMachineBase) world.getTileEntity(new BlockPos(x, y, z));
        this.playerInventory = player.inventory;
        this.machineBase.initContainer(this);
        int i;
        int j;
        for(i = 0; i < 3; ++i){
            for(j = 0; j < 9; ++j){
                addSlotToContainer(new Slot(player.inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        for(i = 0; i < 9; ++i){
            addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }
    }

    public boolean canInteractWith(EntityPlayer p_75145_1_){
        return machineBase.isUseableByPlayer(p_75145_1_);
    }

    public Slot addSlotToContainer(Slot slotIn){
        return super.addSlotToContainer(slotIn);
    }
}
