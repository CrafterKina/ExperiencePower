package com.mods.kina.ExperiencePower.slot;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotFiltered extends Slot{
    public ItemStack[] filterItems;

    public SlotFiltered(IInventory inventory, int id, int x, int y, ItemStack... filter){
        super(inventory, id, x, y);
        filterItems = filter;
    }

    public boolean isItemValid(ItemStack stack){
        for(ItemStack filter : filterItems){
            if(stack.isItemEqual(filter)) return true;
        }
        return false;
    }
}
