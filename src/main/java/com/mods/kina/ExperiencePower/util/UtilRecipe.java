package com.mods.kina.ExperiencePower.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mods.kina.ExperiencePower.recipe.IRecipeWithExp;
import com.mods.kina.ExperiencePower.recipe.ShapedRecipesWithExp;
import com.mods.kina.ExperiencePower.recipe.ShapelessRecipesWithExp;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.world.World;

import java.util.*;

public class UtilRecipe{
    public static final UtilRecipe instance = new UtilRecipe();
    //////////Workbench//////////
    private Set<IRecipeWithExp> epWorkbenchRecipes = Sets.newHashSet();

    private UtilRecipe(){
        addShapelessRecipeToWorkbench(new ItemStack(Items.stick), 5, 0, Items.apple);
    }

    public ShapedRecipes addShapedRecipeToWorkbench(ItemStack stack, int maxLevel, int minLevel, Object... recipeComponents){
        String s = "";
        int i = 0;
        int j = 0;
        int k = 0;

        if(recipeComponents[i] instanceof String[]){
            String[] astring = (String[]) recipeComponents[i++];

            for(String s1 : astring){
                ++k;
                j = s1.length();
                s = s + s1;
            }
        }else{
            while(recipeComponents[i] instanceof String){
                String s2 = (String) recipeComponents[i++];
                ++k;
                j = s2.length();
                s = s + s2;
            }
        }

        HashMap<Character,ItemStack> hashmap;

        for(hashmap = Maps.newHashMap(); i < recipeComponents.length; i += 2){
            Character character = (Character) recipeComponents[i];
            ItemStack itemstack1 = null;

            if(recipeComponents[i + 1] instanceof Item){
                itemstack1 = new ItemStack((Item) recipeComponents[i + 1]);
            }else if(recipeComponents[i + 1] instanceof Block){
                itemstack1 = new ItemStack((Block) recipeComponents[i + 1], 1, 32767);
            }else if(recipeComponents[i + 1] instanceof ItemStack){
                itemstack1 = (ItemStack) recipeComponents[i + 1];
            }

            hashmap.put(character, itemstack1);
        }

        ItemStack[] aitemstack = new ItemStack[j * k];

        for(int i1 = 0; i1 < j * k; ++i1){
            char c0 = s.charAt(i1);

            if(hashmap.containsKey(Character.valueOf(c0))){
                aitemstack[i1] = hashmap.get(Character.valueOf(c0)).copy();
            }else{
                aitemstack[i1] = null;
            }
        }

        ShapedRecipesWithExp shapedrecipes = new ShapedRecipesWithExp(j, k, aitemstack, stack, maxLevel, minLevel);
        epWorkbenchRecipes.add(shapedrecipes);
        return shapedrecipes;
    }

    public void addShapelessRecipeToWorkbench(ItemStack stack, int maxLevel, int minLevel, Object... recipeComponents){
        ArrayList<ItemStack> arraylist = Lists.newArrayList();

        for(Object object1 : recipeComponents){
            if(object1 instanceof ItemStack){
                arraylist.add(((ItemStack) object1).copy());
            }else if(object1 instanceof Item){
                arraylist.add(new ItemStack((Item) object1));
            }else if(object1 instanceof Block){
                arraylist.add(new ItemStack((Block) object1));
            }else{
                throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + object1.getClass().getName() + "!");
            }
        }
        epWorkbenchRecipes.add(new ShapelessRecipesWithExp(stack, arraylist, maxLevel, minLevel));
    }

    public void addWorkbenchRecipes(IRecipeWithExp... recipes){
        Collections.addAll(epWorkbenchRecipes, recipes);
    }

    public IRecipeWithExp findMatchingWorkbenchRecipe(InventoryCrafting p_82787_1_, World worldIn){
        Iterator iterator = epWorkbenchRecipes.iterator();
        IRecipeWithExp irecipe;
        do{
            if(!iterator.hasNext()){
                return null;
            }
            irecipe = (IRecipeWithExp) iterator.next();
        } while(!irecipe.matches(p_82787_1_, worldIn));
        return irecipe;
    }

    public ItemStack[] getRemainingItemsInWorkbench(InventoryCrafting p_180303_1_, World worldIn){

        for(Object epWorkbenchRecipe : epWorkbenchRecipes){
            IRecipe irecipe = (IRecipe) epWorkbenchRecipe;

            if(irecipe.matches(p_180303_1_, worldIn)){
                return irecipe.getRemainingItems(p_180303_1_);
            }
        }

        ItemStack[] aitemstack = new ItemStack[p_180303_1_.getSizeInventory()];

        for(int i = 0; i < aitemstack.length; ++i){
            aitemstack[i] = p_180303_1_.getStackInSlot(i);
        }

        return aitemstack;
    }

    public Set<IRecipeWithExp> getEpWorkbenchRecipes(){
        return epWorkbenchRecipes;
    }
}
