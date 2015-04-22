package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPContainerBase;
import com.mods.kina.ExperiencePower.base.IWrenchable;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.tileentity.TileEntityBasicPipe;
import com.mods.kina.ExperiencePower.util.UtilTileEntity;
import com.mods.kina.KinaCore.misclib.BlockFieldHelper;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.inputColor;
import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.outputColor;

public class BlockBasicPipe extends BlockEPContainerBase implements IWrenchable, IWrenchingInfo{
    public static final PropertyEnum in = PropertyEnum.create("in", optionalFacing.class);
    public static final PropertyEnum out = PropertyEnum.create("out", optionalFacing.class);

    public BlockBasicPipe(){
        super(Material.rock);
        setUnlocalizedName("item_pipe");
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setDefaultState(blockState.getBaseState().withProperty(in, optionalFacing.NONE).withProperty(out, optionalFacing.NONE));
        setHardness(3.0F);
        setResistance(8.0F);
    }

    public static boolean isNone(IBlockState state, IProperty property){
        return ((optionalFacing) state.getValue(property)).ordinal() == 0;
    }

    public static optionalFacing convertToOptional(EnumFacing face){
        return optionalFacing.valueOf(face.name());
    }

    public static EnumFacing convertToNormal(optionalFacing face){
        return EnumFacing.valueOf(face.name());
    }

    public static EnumFacing getFace(IBlockState state, IProperty property){
        if(isNone(state, property)) return null;
        return EnumFacing.values()[((optionalFacing) state.getValue(property)).ordinal() - 1];
    }

    public void postRegister(){
        UtilTileEntity.instance.registerTileEntity(TileEntityBasicPipe.class, "basic_pipe");
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos){
        float f = 0.0625F;
        this.setBlockBounds(f * 4, f * 4, f * 4, f * 12, f * 12, f * 12);
    }

    public void addCollisionBoxesToList(World worldIn, BlockPos pos, IBlockState state, AxisAlignedBB mask, List list, Entity collidingEntity){
        float f = 0.0625F;
        this.setBlockBounds(f * 4, f * 4, f * 4, f * 12, f * 12, f * 12);
        super.addCollisionBoxesToList(worldIn, pos, state, mask, list, collidingEntity);
    }

    public boolean wrench(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        return ((TileEntityBasicPipe) worldIn.getTileEntity(pos)).wrench(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        BlockFieldHelper.instance.putField(pos,"placeFace",facing);
        return getDefaultState();
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        TileEntityBasicPipe pipe = (TileEntityBasicPipe) worldIn.getTileEntity(pos);
        pipe.in = convertToOptional(((EnumFacing) BlockFieldHelper.instance.getField(pos, "placeFace")).getOpposite());
        pipe.out = convertToOptional(BlockPistonBase.getFacingFromEntity(worldIn, pos, placer));
        UtilTileEntity.instance.syncTileEntity(pipe);
    }

    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        if(!(worldIn.getTileEntity(pos) instanceof TileEntityBasicPipe)) return worldIn.getBlockState(pos);
        TileEntityBasicPipe pipe = (TileEntityBasicPipe) worldIn.getTileEntity(pos);
        return getDefaultState().withProperty(in, pipe.in).withProperty(out, pipe.out);
    }

    public int getMetaFromState(IBlockState state){
        return 0;
    }

    protected BlockState createBlockState(){
        return new BlockState(this, in, out);
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.

     @param world
     Worldのインスタンス
     @param meta
     設置されるBlockのメタデータ値。
     */
    public TileEntity createNewTileEntity(World world, int meta){
        return new TileEntityBasicPipe();
    }

    public boolean isFullCube(){
        return false;
    }

    public boolean isOpaqueCube(){
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side){
        return true;
    }

    public boolean hasComparatorInputOverride(){
        return true;
    }

    public int getComparatorInputOverride(World worldIn, BlockPos pos){
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    public void renderInfo(World world, BlockPos pos, double d0, double d1, double d2){
        IBlockState state = world.getBlockState(pos).getBlock().getActualState(world.getBlockState(pos), world, pos);
        for(EnumFacing d : EnumFacing.values()){
            if(!isNone(state, in) && convertToNormal((optionalFacing) state.getValue(in)) == d){
                RenderGlobal.drawOutlinedBoundingBox(getToDrawBox(pos, d0, d1, d2, d), inputColor);
            }
        }

        for(EnumFacing d : EnumFacing.values()){
            if(!isNone(state, out) && convertToNormal((optionalFacing) state.getValue(out)) == d){
                RenderGlobal.drawOutlinedBoundingBox(getToDrawBox(pos, d0, d1, d2, d), outputColor);
            }
        }
    }

    public EnumDyeColor getWrenchColor(ItemStack stack, World worldIn, Entity entityIn, MovingObjectPosition position, boolean isUsing){
        IBlockState pipe = worldIn.getBlockState(position.getBlockPos());
        if(!entityIn.isSneaking()){
            if(isNone(pipe, in) || !isNone(pipe, out)) return EnumDyeColor.CYAN;
            else if(!isNone(pipe, in) || isNone(pipe, out)) return EnumDyeColor.RED;
        }else{
            if(isNone(pipe, in) || !isNone(pipe, out)) return EnumDyeColor.RED;
            if(!isNone(pipe, in) || isNone(pipe, out)) return EnumDyeColor.CYAN;
        }
        return EnumDyeColor.WHITE;
    }

    public int getWrenchColor(World worldIn, ItemStack stack, Entity entityIn, MovingObjectPosition position, boolean isUsing){
        return ConfigurableFieldCollection.defaultDyeColor[getWrenchColor(stack, worldIn, entityIn, position, isUsing).getDyeDamage()];
    }

    private AxisAlignedBB getToDrawBox(BlockPos pos, double d0, double d1, double d2, EnumFacing d){
        float f1 = 0.002F;
        float f2 = 0.0625f;
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        AxisAlignedBB base = new AxisAlignedBB(f2 * 4, f2 * 4, f2 * 4, f2 * 12, f2 * 12, f2 * 12);
        double[] grid = new double[]{f2 * 2 + d.getFrontOffsetX() * 6 * f2, f2 * 2 + d.getFrontOffsetY() * 6 * f2, f2 * 2 + d.getFrontOffsetZ() * 6 * f2, f2 * -2 + d.getFrontOffsetX() * 6 * f2, f2 * -2 + d.getFrontOffsetY() * 6 * f2, f2 * -2 + d.getFrontOffsetZ() * 6 * f2};
        AxisAlignedBB box = new AxisAlignedBB(x + base.minX + grid[0], y + base.minY + grid[1], z + base.minZ + grid[2], x + base.maxX + grid[3], y + base.maxY + grid[4], z + base.maxZ + grid[5]);
        box = box.expand(f1, f1, f1).offset(-d0, -d1, -d2);
        return box;
    }

    public enum optionalFacing implements IStringSerializable{
        NONE,
        DOWN,
        UP,
        NORTH,
        SOUTH,
        WEST,
        EAST,;

        public String getName(){
            return name().toLowerCase();
        }
    }
}
