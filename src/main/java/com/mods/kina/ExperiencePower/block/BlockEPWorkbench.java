package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.collection.EnumEPGuiContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEPWorkbench extends BlockEPBase{
    public BlockEPWorkbench(){
        super(Material.wood);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
        setUnlocalizedName("work_bench");
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setHardness(2.5F);
        setStepSound(soundTypeWood);
    }

    public boolean isOpaqueCube(){
        return false;
    }

    public boolean isFullCube(){
        return false;
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!worldIn.isRemote) EnumEPGuiContainer.Workbench.openGui(worldIn, pos, playerIn);
        return true;
    }
}
