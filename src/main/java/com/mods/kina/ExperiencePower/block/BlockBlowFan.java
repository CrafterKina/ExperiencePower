package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPContainerBase;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.tileentity.TileEntityBlowFan;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.awt.*;

public class BlockBlowFan extends BlockEPContainerBase implements IWrenchingInfo{
    public static final PropertyDirection FACING = PropertyDirection.create("facing");

    public BlockBlowFan(){
        setUnlocalizedName("blow_fan");
        setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setHardness(1.5f);
        setResistance(10);
        UtilTileEntity.instance.registerTileEntity(TileEntityBlowFan.class, "blow_Fan");
    }

    /**
     置き方で向きを変える。
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer)), 2);
    }

    /**
     置き方で向きを変える。
     */
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return blockState.getBaseState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
    }

    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacing(meta));
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

    public void renderInfo(World world, BlockPos pos, double d0, double d1, double d2){
        TileEntityBlowFan blowFan = (TileEntityBlowFan) world.getTileEntity(pos);
        //向きを取得
        EnumFacing facing = (EnumFacing) world.getBlockState(pos).getProperties().get(BlockBlowFan.FACING);
        //向いてるとこの相対的位置を取得
        Vec3i vec3i = facing.getDirectionVec();
        //初期化
        AxisAlignedBB aabb;
        //相対的位置が+方向か-方向かで場合分け
        //範囲指定
        if(facing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE){
            aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1 + vec3i.getX() * blowFan.getAvailableDistance(), pos.getY() + 1 + vec3i.getY() * blowFan.getAvailableDistance(), pos.getZ() + 1 + vec3i.getZ() * blowFan.getAvailableDistance());
        }else{
            aabb = new AxisAlignedBB(pos.getX() + vec3i.getX() * blowFan.getAvailableDistance(), pos.getY() + vec3i.getY() * blowFan.getAvailableDistance(), pos.getZ() + vec3i.getZ() * blowFan.getAvailableDistance(), pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1);
        }
        float f1 = 0.002F;
        aabb = aabb.expand(f1, f1, f1).offset(-d0, -d1, -d2);
        RenderGlobal.drawOutlinedBoundingBox(aabb, Color.green.getRGB());
    }

    public EnumDyeColor getWrenchColor(ItemStack stack, World worldIn, Entity entityIn, MovingObjectPosition position, boolean isUsing){
        return EnumDyeColor.WHITE;
    }
}
