package com.mods.kina.ExperiencePower.recipe;

import com.google.common.collect.Lists;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class NBTShapelessRecipes extends ShapelessRecipes{
    public NBTShapelessRecipes(ItemStack output, List inputList){
        super(output, inputList);
    }

    public boolean matches(InventoryCrafting p_77569_1_, World worldIn){
        ArrayList arraylist = Lists.newArrayList(this.recipeItems);

        for(int i = 0; i < p_77569_1_.getHeight(); ++i){
            for(int j = 0; j < p_77569_1_.getWidth(); ++j){
                ItemStack itemstack = p_77569_1_.getStackInRowAndColumn(j, i);

                if(itemstack != null){
                    boolean flag = false;

                    for(Object anArraylist : arraylist){
                        ItemStack itemstack1 = (ItemStack) anArraylist;

                        if(itemstack.getIsItemStackEqual(itemstack1)){
                            flag = true;
                            arraylist.remove(itemstack1);
                            break;
                        }
                    }

                    if(!flag){
                        return false;
                    }
                }
            }
        }

        return arraylist.isEmpty();
    }
}
