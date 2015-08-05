package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPContainerBase;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
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
     Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacing(meta));
    }

    /**
     Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state){
        return ((EnumFacing) state.getValue(FACING)).getIndex();
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
        return new BlockState(this, FACING);
    }

    public void renderInfo(World world, BlockPos pos, double d0, double d1, double d2){
        TileEntityBlowFan blowFan = (TileEntityBlowFan) world.getTileEntity(pos);
        float f1 = 0.002F;
        if(world.isBlockIndirectlyGettingPowered(pos) == 0) return;
        if(blowFan.getAvailableRange() == null) return;
        RenderGlobal.drawOutlinedBoundingBox(blowFan.getAvailableRange()/*.expand(f1, f1, f1)*/.offset(-d0, -d1, -d2), Color.green.getRGB());
    }

    public EnumDyeColor getWrenchColor(ItemStack stack, World worldIn, Entity entityIn, MovingObjectPosition position, boolean isUsing){
        return EnumDyeColor.WHITE;
    }

    public int getWrenchColor(World worldIn, ItemStack stack, Entity entityIn, MovingObjectPosition position, boolean isUsing){
        return ConfigurableFieldCollection.defaultDyeColor[getWrenchColor(stack, worldIn, entityIn, position, isUsing).getDyeDamage()];
    }
}
