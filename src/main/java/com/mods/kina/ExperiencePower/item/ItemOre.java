package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.block.BlockOre;
import net.minecraft.block.Block;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;

public class ItemOre extends ItemColored{
    public ItemOre(Block p_i45332_1_){
        super(p_i45332_1_, true);
    }

    /**
     Returns the unlocalized name of this item. This version accepts an ItemStack so different stacks can have different
     names based on their damage or NBT.
     */
    public String getUnlocalizedName(ItemStack stack){
        int i = stack.getMetadata();
        return "kina.tile.ore_" + BlockOre.OreType.values()[i].getName();
    }
}
