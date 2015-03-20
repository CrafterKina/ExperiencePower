package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.block.BlockOre;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistrar{
    public static void registerOres(){
        for(int i = 0; i < BlockOre.OreType.values().length; i++){
            OreDictionary.registerOre("ore" + getCamelName(BlockOre.OreType.values()[i].getName()), new ItemStack(EnumEPBlock.Ore.getBlock(), 1, i));
            OreDictionary.registerOre("ingot" + getCamelName(BlockOre.OreType.values()[i].getName()), new ItemStack(EnumEPItem.Ingot.getItem(), 1, i));
        }
        registerOres("fenceWood", new ItemStack(Blocks.oak_fence), new ItemStack(Blocks.spruce_fence), new ItemStack(Blocks.birch_fence), new ItemStack(Blocks.jungle_fence), new ItemStack(Blocks.dark_oak_fence), new ItemStack(Blocks.acacia_fence));
    }

    private static String getCamelName(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }

    private static void registerOres(String name, ItemStack... itemStacks){
        for(ItemStack itemStack : itemStacks){
            OreDictionary.registerOre(name, itemStack);
        }
    }
}
