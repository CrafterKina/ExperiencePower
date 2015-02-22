package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.block.BlockBasicPipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemBasicPipe extends ItemBlock{
    public ItemBasicPipe(Block block){
        super(block);
    }

    /**
     Called when a Block is right-clicked with this Item

     @param pos
     The block being right-clicked
     @param side
     The side being right-clicked
     */
    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        worldIn.setBlockState(pos, this.block.getDefaultState().withProperty(BlockBasicPipe.in, BlockBasicPipe.optionalFacing.NONE).withProperty(BlockBasicPipe.out, BlockBasicPipe.optionalFacing.NONE));
        return true;
    }
}
