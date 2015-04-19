package com.mods.kina.ExperiencePower.base;

import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public interface IWrenchingInfo{
    void renderInfo(World world, BlockPos pos, double d0, double d1, double d2);

    @Deprecated
    public EnumDyeColor getWrenchColor(ItemStack stack, World worldIn, Entity entityIn, MovingObjectPosition position, boolean isUsing);

    public int getWrenchColor(World worldIn, ItemStack stack, Entity entityIn, MovingObjectPosition position, boolean isUsing);
}
