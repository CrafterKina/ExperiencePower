package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.ExperiencePowerCore;
import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPGui;
import com.mods.kina.ExperiencePower.tileentity.TileEntityExpDischarger;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 経験をくれるいいやつ
 */
public class BlockExpDischarger extends BlockMachineBase{
    public BlockExpDischarger(){
        super(Material.rock);
        setUnlocalizedName("exp_discharger");
        GameRegistry.registerTileEntity(TileEntityExpDischarger.class, "TileEntityExpDischarger");
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!worldIn.isRemote)
            playerIn.openGui(ExperiencePowerCore.core, EnumEPGui.ExperienceDischarger.ordinal(), worldIn, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world
     Worldのインスタンス
     @param meta
     設置されるBlockのメタデータ値。
     */
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityExpDischarger();
    }
}
