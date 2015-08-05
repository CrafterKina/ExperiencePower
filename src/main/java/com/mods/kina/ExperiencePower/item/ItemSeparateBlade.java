package com.mods.kina.ExperiencePower.item;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mods.kina.ExperiencePower.base.IEPRecipe;
import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.recipe.RotaryMachineRecipe;
import com.mods.kina.KinaCore.misclib.KinaLib;
import com.mods.kina.KinaCore.toExtends.IHasInfo;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

import java.util.Map;
import java.util.Set;

public class ItemSeparateBlade extends ItemEPBase implements IHasInfo{
    Set<IEPRecipe> recipes = Sets.newHashSet();

    public ItemSeparateBlade(){
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setUnlocalizedName("separate_blade");
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PINK.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.ORANGE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.YELLOW.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIME.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.GRAY.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLACK.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.SILVER.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GRAY.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.LIGHT_BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.WHITE.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.CYAN.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.GREEN.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.PURPLE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.RED.getDyeDamage()), 1, 10));
        recipes.add(new RotaryMachineRecipe(new ItemStack(Items.dye, 2, EnumDyeColor.MAGENTA.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PURPLE.getDyeDamage()), new ItemStack(Items.dye, 1, EnumDyeColor.PINK.getDyeDamage()), 1, 10));
    }

    @SuppressWarnings("unchecked")
    public Map<String,?> info(Map<String,?> input){
        Map<String,Object> result = Maps.newHashMap();
        result.put("rotary.available", true);
        for(IEPRecipe recipe : recipes){
            Map<String,Object> info = (Map<String,Object>) recipe.info(input);
            if((Integer) KinaLib.lib.getOrDefault(info, "times.needed", -1) == -1) continue;
            result.putAll(info);
            result.put("rotary.match", true);
            return result;
        }
        result.put("rotary.match", false);
        return result;
    }
}
