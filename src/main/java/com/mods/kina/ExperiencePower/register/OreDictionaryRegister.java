package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.block.BlockOre;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryRegister{
    public static void registerOres(){
        for(int i = 0; i < BlockOre.OreType.values().length; i++){
            OreDictionary.registerOre("ore" + getCamelName(BlockOre.OreType.values()[i].getName()), new ItemStack(EnumEPBlock.Ore.getBlock(), 1, i));
            OreDictionary.registerOre("ingot" + getCamelName(BlockOre.OreType.values()[i].getName()), new ItemStack(EnumEPItem.Ingot.getItem(), 1, i));
        }
    }

    private static String getCamelName(String s){
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
