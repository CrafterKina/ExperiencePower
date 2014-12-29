package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.tileentity.TileEntityExpAbsorber;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 経験を吸いとる怖いやつ
 */
public class BlockExpAbsorber extends BlockMachineBase{
    public BlockExpAbsorber(){
        super(Material.rock);
        setUnlocalizedName("exp_absorber");
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world
     Worldのインスタンス
     @param meta
     設置されるBlockのメタデータ値。
     */
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityExpAbsorber();
    }
}
