package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.annotation.EPProp;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import com.mods.kina.ExperiencePower.collection.EnumMetal;
import com.mods.kina.ExperiencePower.item.ItemMold;
import com.mods.kina.KinaCore.event.KinaCoreHooks;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class SmeltRecipeRegistrar{
    @EPProp(comment = "[WIP]remove easier recipes added by other mods.")
    public static final boolean disableEasierRecipes = false;

    public static void registerRecipes(){
        registerBucket();
        removeSmelt();
        registerSmelt();
        registerFuel();
    }

    private static void registerSmelt(){
        for(EnumMetal metal : EnumMetal.values()){
            ItemStack mold = new ItemStack(EnumEPItem.Mold.getItem());
            mold.getSubCompound("cast", true).setString("content", metal.name());
            for(ItemStack ore : metal.getOre()){
                GameRegistry.addSmelting(ore, mold, 0.7f);
            }

            for(ItemStack ingot : metal.getIngot()){
                GameRegistry.addSmelting(ingot, mold, 0);
            }
        }

        for(ItemMold.Type type : ItemMold.Type.values()){
            GameRegistry.addSmelting(ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), type.name(), "empty", false), ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), type.name(), "empty", true), 0);
        }
    }

    public static void removeSmelt(){
        if(disableEasierRecipes){
            FurnaceRecipes.instance().getSmeltingList().remove(Blocks.iron_ore);
            FurnaceRecipes.instance().getSmeltingList().remove(Blocks.gold_ore);
            for(EnumMetal metal : EnumMetal.values())
                for(ItemStack ore : metal.getOre()){
                    FurnaceRecipes.instance().getSmeltingList().remove(ore);
                    FurnaceRecipes.instance().getSmeltingList().remove(ore.getItem());
                }
        }
    }

    private static void registerBucket(){
        for(ItemMold.Type type : ItemMold.Type.values()){
            KinaCoreHooks.getContainerSet().add(ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), type.name(), "empty", true));
        }
    }

    private static void registerFuel(){

    }
}
