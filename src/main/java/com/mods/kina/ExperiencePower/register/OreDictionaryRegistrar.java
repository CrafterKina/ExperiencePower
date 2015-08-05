package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.block.BlockOre;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import com.mods.kina.ExperiencePower.collection.EnumMetal;
import com.mods.kina.ExperiencePower.item.ItemCast;
import com.mods.kina.ExperiencePower.item.ItemMold;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegistrar{
    public static void registerOres(){
        for(int i = 0; i < BlockOre.OreType.values().length; i++){
            OreDictionary.registerOre("ore" + getCamelName(BlockOre.OreType.values()[i].getName()), new ItemStack(EnumEPBlock.Ore.getBlock(), 1, i));
        }
        for(ItemMold.Type type : ItemMold.Type.values())
            for(EnumMetal metal : EnumMetal.values()){
                OreDictionary.registerOre(type.name().toLowerCase() + metal.name(), ItemCast.setNBT(new ItemStack(EnumEPItem.Cast.getItem()), type.name(), metal.name()));
                OreDictionary.registerOre("mold" + type.name() + metal.name(), ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), type.name(), metal.name(), true));
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
