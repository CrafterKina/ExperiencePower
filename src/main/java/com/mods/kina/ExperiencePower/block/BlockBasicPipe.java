package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockEPContainerBase;
import com.mods.kina.ExperiencePower.base.IWrenchable;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.tileentity.TileEntityBasicPipe;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class BlockBasicPipe extends BlockEPContainerBase implements IWrenchable, IWrenchingInfo{
    public static final PropertyEnum in = PropertyEnum.create("in", optionalFacing.class);
    public static final PropertyEnum out = PropertyEnum.create("out", optionalFacing.class);

    public BlockBasicPipe(){
        super(Material.rock);
        setUnlocalizedName("item_pipe");
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setDefaultState(blockState.getBaseState().withProperty(in, optionalFacing.NONE).withProperty(out, optionalFacing.NONE));
        GameRegistry.registerTileEntity(TileEntityBasicPipe.class, "basic_pipe");
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

    public void renderInfo(World world, BlockPos pos, double d0, double d1, double d2){
        IBlockState state = world.getBlockState(pos).getBlock().getActualState(world.getBlockState(pos), world, pos);
        for(EnumFacing d : EnumFacing.values()){
            if(!isNone(state, in) && convertToNormal((optionalFacing) state.getValue(in)) == d){
                GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.7F);
                this.drawBox(pos, d0, d1, d2, d);
            }
        }

        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);

        for(EnumFacing d : EnumFacing.values()){
            if(!isNone(state, out) && convertToNormal((optionalFacing) state.getValue(out)) == d){
                GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.8F);
                this.drawBox(pos, d0, d1, d2, d);
            }
        }

        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
    }

    private void drawBox(BlockPos pos, double d0, double d1, double d2, EnumFacing d){
        float f1 = 0.002F;
        float f2 = 0.0625f;
        double x2 = pos.getX();
        double y2 = pos.getY();
        double z2 = pos.getZ();
        AxisAlignedBB base = new AxisAlignedBB(f2 * 4, f2 * 4, f2 * 4, f2 * 12, f2 * 12, f2 * 12);
        double[] grid = new double[]{f2 * 2 + d.getFrontOffsetX() * 6 * f2, f2 * 2 + d.getFrontOffsetY() * 6 * f2, f2 * 2 + d.getFrontOffsetZ() * 6 * f2, f2 * -2 + d.getFrontOffsetX() * 6 * f2, f2 * -2 + d.getFrontOffsetY() * 6 * f2, f2 * -2 + d.getFrontOffsetZ() * 6 * f2};
        AxisAlignedBB axisAlignedBB2 = new AxisAlignedBB(x2 + base.minX + grid[0], y2 + base.minY + grid[1], z2 + base.minZ + grid[2], x2 + base.maxX + grid[3], y2 + base.maxY + grid[4], z2 + base.maxZ + grid[5]);
        axisAlignedBB2 = axisAlignedBB2.expand(f1, f1, f1).offset(-d0, -d1, -d2);
        this.drawOutlinedBoundingBox(axisAlignedBB2);
        GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
    }

    private void drawOutlinedBoundingBox(AxisAlignedBB aabb){
        Tessellator tessellator = Tessellator.getInstance();
        tessellator.getWorldRenderer().startDrawing(3);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.minY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.minY, aabb.minZ);
        tessellator.draw();
        tessellator.getWorldRenderer().startDrawing(3);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        tessellator.draw();
        tessellator.getWorldRenderer().startDrawing(1);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.minY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.maxY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.minY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.minY, aabb.maxZ);
        tessellator.getWorldRenderer().addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
        tessellator.draw();
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
