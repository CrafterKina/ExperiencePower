package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.base.IWrenchable;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemWrenchWand extends Item{
    public ItemWrenchWand(){
        setUnlocalizedName("wrench_wand");
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        return worldIn.getBlockState(pos).getBlock() instanceof IWrenchable && ((IWrenchable) worldIn.getBlockState(pos).getBlock()).wrench(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }
}
