package com.mods.kina.ExperiencePower.base;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEPBase extends Item{
    public ItemEPBase(){
    }

    public String getUnlocalizedName(){
        return super.getUnlocalizedName().replaceFirst("item\\.", "kina.item.");
    }

    public String getUnlocalizedName(ItemStack stack){
        return getUnlocalizedName();
    }
}
