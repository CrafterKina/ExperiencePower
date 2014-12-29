package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 経験をくれるいいやつ
 */
public class BlockExpDischarger extends BlockMachineBase{
    public BlockExpDischarger(){
        super(Material.rock);
        setUnlocalizedName("exp_discharger");
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world
     Worldのインスタンス
     @param meta
     設置されるBlockのメタデータ値。
     */
    public TileEntity createNewTileEntity(World world, int meta){
        return null;
    }
}
