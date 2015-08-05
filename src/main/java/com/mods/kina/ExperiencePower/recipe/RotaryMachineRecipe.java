package com.mods.kina.ExperiencePower.recipe;

import com.google.common.collect.Maps;
import com.mods.kina.ExperiencePower.base.IEPRecipe;
import com.mods.kina.ExperiencePower.tileentity.TileEntityRotaryMachine;
import com.mods.kina.KinaCore.misclib.KinaLib;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.Map;
import java.util.Random;

public class RotaryMachineRecipe implements IEPRecipe{
    private final ItemStack input;
    private final ItemStack output;
    private final ItemStack rareout;
    private final float rarity;
    private final int needPower;
    private Random random = new Random();

    public RotaryMachineRecipe(ItemStack input, ItemStack output, ItemStack rareout, float rarity, int needPower){
        this.input = input;
        this.output = output;
        this.rareout = rareout;
        this.rarity = rarity;
        this.needPower = needPower;
    }

    public Map<String,?> info(Map<String,?> data){
        IInventory inventory = (IInventory) data.get("inventory");
        if(!(inventory instanceof TileEntityRotaryMachine)) return Collections.emptyMap();
        Map<String,Object> ret = Maps.newHashMap();
        ret.put("times.needed", needPower);
        ItemStack leftI = ItemStack.copyItemStack(inventory.getStackInSlot(1));
        ItemStack rightI = ItemStack.copyItemStack(inventory.getStackInSlot(2));
        ItemStack leftO = ItemStack.copyItemStack(inventory.getStackInSlot(3));
        ItemStack rightO = ItemStack.copyItemStack(inventory.getStackInSlot(4));
        ItemStack rare = ItemStack.copyItemStack(inventory.getStackInSlot(5));
        (isStackEqual(leftI, input) ? leftI : (isStackEqual(rightI, input) ? rightI : new ItemStack(Items.diamond))).stackSize -= input.stackSize;
        if(ItemStack.areItemStacksEqual(leftI, inventory.getStackInSlot(1)) && ItemStack.areItemStacksEqual(rightI, inventory.getStackInSlot(2)))
            return Collections.emptyMap();
        if((Integer) data.get("times") < needPower) return ret;
        (canMerge(leftO, output) ? leftO : canMerge(rightO, output) ? rightO : new ItemStack(Blocks.dirt)).stackSize += output.stackSize;
        (canMerge(rare, rareout) ? rare : new ItemStack(Items.apple)).stackSize += rareout.stackSize;
        if(rare == null && random.nextInt(100) < rarity * 100) rare = rareout;
        ItemStack[] result = {inventory.getStackInSlot(0), leftI, rightI, leftO, rightO, rare};
        if(ItemStack.areItemStacksEqual(leftO, inventory.getStackInSlot(3)) && ItemStack.areItemStacksEqual(rightO, inventory.getStackInSlot(4))){
            if(leftO != null && rightO != null) return ret;
            result[leftO == null ? 3 : 4] = output;
        }
        ret.put("result", result);
        return ret;
    }

    private boolean isStackEqual(ItemStack l, ItemStack r){
        return l != null && r != null && KinaLib.lib.areItemStacksEqualForRecipe(l, r) && l.stackSize >= r.stackSize;
    }

    private boolean canMerge(ItemStack l, ItemStack r){
        return (l != null) && (r != null) && ItemStack.areItemsEqual(l, r) && ((l.stackSize + r.stackSize) <= l.getMaxStackSize());
    }
}
