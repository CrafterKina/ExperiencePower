package com.mods.kina.ExperiencePower.plugin.nei.handler;

import codechicken.nei.NEIServerUtils;
import codechicken.nei.recipe.ShapedRecipeHandler;
import com.mods.kina.ExperiencePower.gui.GuiEPWorkbench;
import com.mods.kina.ExperiencePower.recipe.ShapedRecipesWithExp;
import com.mods.kina.ExperiencePower.util.UtilRecipe;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;

import java.awt.*;

public class ShapedRecipeWithExpHandler extends ShapedRecipeHandler{
    public String getRecipeName(){
        return "Shaped Recipe With Exp";
    }

    @Override
    public void loadTransferRects(){
        transferRects.add(new RecipeTransferRect(new Rectangle(84, 23, 24, 18), "ep_crafting"));
    }

    public Class<? extends GuiContainer> getGuiClass(){
        return GuiEPWorkbench.class;
    }

    @Override
    public String getGuiTexture(){
        return "textures/gui/container/crafting_table.png";
    }

    @Override
    public String getOverlayIdentifier(){
        return "ep_crafting";
    }

    @Override
    public void loadCraftingRecipes(String outputId, Object... results){
        if(outputId.equals("ep_crafting")){
            for(IRecipe irecipe : UtilRecipe.instance.getEpWorkbenchRecipes()){
                CachedShapedRecipe recipe = null;
                if(irecipe instanceof ShapedRecipesWithExp)
                    recipe = new CachedShapedRecipe((ShapedRecipesWithExp) irecipe);

                if(recipe == null) continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }else{
            super.loadCraftingRecipes(outputId, results);
        }
    }

    @Override
    public void loadCraftingRecipes(ItemStack result){
        for(IRecipe irecipe : UtilRecipe.instance.getEpWorkbenchRecipes()){
            if(NEIServerUtils.areStacksSameTypeCrafting(irecipe.getRecipeOutput(), result)){
                CachedShapedRecipe recipe = null;
                if(irecipe instanceof ShapedRecipesWithExp)
                    recipe = new CachedShapedRecipe((ShapedRecipesWithExp) irecipe);

                if(recipe == null) continue;

                recipe.computeVisuals();
                arecipes.add(recipe);
            }
        }
    }

    @Override
    public void loadUsageRecipes(ItemStack ingredient){
        for(IRecipe irecipe : UtilRecipe.instance.getEpWorkbenchRecipes()){
            CachedShapedRecipe recipe = null;
            if(irecipe instanceof ShapedRecipesWithExp) recipe = new CachedShapedRecipe((ShapedRecipesWithExp) irecipe);

            if(recipe == null || !recipe.contains(recipe.ingredients, ingredient.getItem())) continue;

            recipe.computeVisuals();
            if(recipe.contains(recipe.ingredients, ingredient)){
                recipe.setIngredientPermutation(recipe.ingredients, ingredient);
                arecipes.add(recipe);
            }
        }
    }
}
