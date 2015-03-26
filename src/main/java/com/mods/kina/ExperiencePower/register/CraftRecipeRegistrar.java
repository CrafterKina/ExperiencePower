package com.mods.kina.ExperiencePower.register;

import com.google.common.collect.Lists;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import com.mods.kina.ExperiencePower.collection.EnumMetal;
import com.mods.kina.ExperiencePower.item.ItemMold;
import com.mods.kina.ExperiencePower.recipe.NBTShapelessRecipes;
import com.mods.kina.ExperiencePower.util.UtilRecipe;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static com.mods.kina.ExperiencePower.collection.EnumEPBlock.*;
import static com.mods.kina.ExperiencePower.collection.EnumEPItem.Wrench;

public class CraftRecipeRegistrar{

    /**
     レシピを登録する。
     */
    public static void registerRecipes(){
        registerShapedRecipes();
        registerShapelessRecipes();
    }

    /**
     形の決まったレシピを登録する。
     */
    private static void registerShapedRecipes(){
        GameRegistry.addShapedRecipe(new ItemStack(ItemPipe.getBlock(), 32, 0), "IDI", "I I", "IHI", 'I', Items.iron_ingot, 'D', Blocks.dropper, 'H', Blocks.hopper);
        GameRegistry.addRecipe(new ShapedOreRecipe(Workbench.getBlock(), "HWH", "SBS", 'H', "slabWood", 'W', Blocks.crafting_table, 'S', "fenceWood", 'B', Items.book));
        GameRegistry.addRecipe(new ShapedOreRecipe(BlowFan.getBlock(), "WIW", "CRC", "CCC", 'W', "plankWood", 'I', Items.iron_ingot, 'C', "cobblestone", 'R', Items.redstone));
        GameRegistry.addShapedRecipe(new ItemStack(MachineCore.getBlock()), "IWI", "WCW", "IWI", 'I', Items.iron_ingot, 'W', new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE), 'C', Blocks.chest);
        UtilRecipe.instance.addShapedRecipeToWorkbench(new ItemStack(Wrench.getItem()), 1, 0, "  I", " S ", "S  ", 'I', Items.iron_ingot, 'S', Items.stick);
        GameRegistry.addShapedRecipe(ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), ItemMold.Type.Ingot.name(), "empty", false), "CCC", "C C", "CCC", 'C', Items.clay_ball);
        GameRegistry.addShapedRecipe(ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), ItemMold.Type.Wire.name(), "empty", false), "CCC", "   ", "CCC", 'C', Items.clay_ball);
        GameRegistry.addShapedRecipe(ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), ItemMold.Type.Plate.name(), "empty", false), "C C", " C ", "C C", 'C', Items.clay_ball);
    }

    /**
     形の決まっていないレシピを登録する。
     */
    private static void registerShapelessRecipes(){
        UtilRecipe.instance.addShapelessRecipeToWorkbench(new ItemStack(TrainingBarrel.getBlock()), 5, 0, Blocks.hay_block);
        //UtilRecipe.instance.addShapelessRecipeToWorkbench(new ItemStack(Ingot.getItem(), 1, 3), 10, 0, Items.iron_ingot);
        for(ItemMold.Type type : ItemMold.Type.values())
            for(EnumMetal metal : EnumMetal.values())
                for(ItemStack result : OreDictionary.getOres(type.name().toLowerCase() + metal.name())){
                    GameRegistry.addRecipe(new NBTShapelessRecipes(result, Lists.newArrayList(ItemMold.setNBT(new ItemStack(EnumEPItem.Mold.getItem()), type.name(), metal.name(), true))));
                }
    }
}
