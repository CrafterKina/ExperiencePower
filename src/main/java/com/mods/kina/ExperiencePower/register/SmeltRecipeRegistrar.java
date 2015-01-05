package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.block.BlockOre;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltRecipeRegistrar{
    public static void registerRecipes(){
        registerSmelt();
        registerFuel();
    }

    private static void registerSmelt(){
        for(int i = 0; i < BlockOre.OreType.values().length; i++){
            GameRegistry.addSmelting(new ItemStack(EnumEPBlock.Ore.getBlock(), 1, i), new ItemStack(EnumEPItem.Ingot.getItem(), 1, i), 0);
        }
    }

    private static void registerFuel(){

    }
}
