package com.mods.kina.ExperiencePower.recipe;

import net.minecraft.item.crafting.IRecipe;

public interface IRecipeWithExp extends IRecipe{
    int maxExpLevel();

    int minExpLevel();
}
