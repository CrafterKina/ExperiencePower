package com.mods.kina.ExperiencePower.recipe;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;

public class ShapedRecipesWithExp extends ShapedRecipes implements IRecipeWithExp{
    private final int max;
    private final int min;

    public ShapedRecipesWithExp(int width, int height, ItemStack[] p_i1917_3_, ItemStack output, int maxLevel, int minLevel){
        super(width, height, p_i1917_3_, output);
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
