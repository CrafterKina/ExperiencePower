package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.base.IReceiveEnergy;
import com.mods.kina.ExperiencePower.collection.EnumEPGuiContainer;
import com.mods.kina.ExperiencePower.tileentity.TileEntityExpDischarger;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

/**
 経験をくれるいいやつ
 */
public class BlockExpDischarger extends BlockMachineBase implements IReceiveEnergy{
    public BlockExpDischarger(){
        super(Material.rock);
        setUnlocalizedName("exp_discharger");
        setHardness(1.5f);
        setResistance(10);
        UtilTileEntity.instance.registerTileEntity(TileEntityExpDischarger.class, "exp_discharger");
    }

    /**
     右クリックでGUIを開く。
     */
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        if(!worldIn.isRemote)
            EnumEPGuiContainer.openGui(worldIn, pos, playerIn, EnumEPGuiContainer.ExperienceDischarger);
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
