package com.mods.kina.ExperiencePower.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.List;

public class ShapelessRecipesWithExp extends ShapelessRecipes implements IRecipeWithExp{
    private final int max;
    private final int min;

    public ShapelessRecipesWithExp(ItemStack output, List input, int maxLevel, int minLevel){
        super(output, input);
        max = maxLevel;
        min = minLevel;
    }

    public int maxExpLevel(){
        return max;
    }

    public int minExpLevel(){
        return min;
    }
}
