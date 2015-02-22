package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.entity.core.EntityMonsterBook;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemEPMonsterPlacer extends Item{
    public ItemEPMonsterPlacer(){
        setUnlocalizedName("monster_placer");
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
    }

    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!worldIn.isRemote) worldIn.spawnEntityInWorld(new EntityMonsterBook(worldIn, pos.up()));
        return true;
    }
}
