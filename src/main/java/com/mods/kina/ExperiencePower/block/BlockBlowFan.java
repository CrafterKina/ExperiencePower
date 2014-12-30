package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPContainerBase;
import com.mods.kina.ExperiencePower.tileentity.TileEntityBlowFan;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockBlowFan extends BlockEPContainerBase{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockBlowFan(){
        super(Material.rock);
        setUnlocalizedName("blow_fan");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        GameRegistry.registerTileEntity(TileEntityBlowFan.class, "TileEntityBlowFan");
    }

    /**
     置き方で向きを変える。
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.func_180695_a(worldIn, pos, placer)), 2);
    }

    /**
     置き方で向きを変える。
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return blockState.getBaseState().withProperty(FACING, BlockPistonBase.func_180695_a(worldIn, pos, placer));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.func_176317_b(meta));
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world
     Worldのインスタンス
     @param meta
     設置されるBlockのメタデータ値。
     */
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityBlowFan();
    }

    protected BlockState createBlockState(){
        return new BlockState(this,FACING);
    }
}
