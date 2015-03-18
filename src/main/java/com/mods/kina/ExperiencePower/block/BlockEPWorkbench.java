package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.collection.EnumEPGuiContainer;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEPWorkbench extends Block{
    public BlockEPWorkbench(){
        super(Material.rock);
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!worldIn.isRemote)
            playerIn.openGui(StaticFieldCollection.epCore, EnumEPGuiContainer.EPWorkbench.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }
}
