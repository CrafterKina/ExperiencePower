package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockEPContainerBase extends BlockContainer{
    public BlockEPContainerBase(Material material){
        super(material);
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if(tileentity instanceof IInventory){
            InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory) tileentity);
            worldIn.updateComparatorOutputLevel(pos, this);
        }

        super.breakBlock(worldIn, pos, state);
    }

    /**
     * The type of render function that is called for this block
     * デフォルトに戻す。
     */
    public int getRenderType()
    {
        return 3;
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world Worldのインスタンス
     @param meta 設置されるBlockのメタデータ値。
     */
    public abstract TileEntity createNewTileEntity(World world, int meta);
}
