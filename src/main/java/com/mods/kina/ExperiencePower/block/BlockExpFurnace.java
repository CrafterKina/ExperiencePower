package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.tileentity.TileEntityExpFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockExpFurnace extends BlockMachineBase{
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    private static boolean field_149934_M;
    private boolean isBurning;

    public BlockExpFurnace(){
        super(Material.rock);
        setUnlocalizedName("exp_furnace");
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        GameRegistry.registerTileEntity(TileEntityExpFurnace.class, "TileEntityExpFurnace");
    }

    protected BlockExpFurnace(boolean p_i45407_1_){
        super(Material.rock);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.isBurning = p_i45407_1_;
    }

    public static void func_176446_a(boolean p_176446_0_, World worldIn, BlockPos p_176446_2_){
        IBlockState iblockstate = worldIn.getBlockState(p_176446_2_);
        TileEntity tileentity = worldIn.getTileEntity(p_176446_2_);
        field_149934_M = true;

        if(p_176446_0_){
            worldIn.setBlockState(p_176446_2_, Blocks.lit_furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(p_176446_2_, Blocks.lit_furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }else{
            worldIn.setBlockState(p_176446_2_, Blocks.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
            worldIn.setBlockState(p_176446_2_, Blocks.furnace.getDefaultState().withProperty(FACING, iblockstate.getValue(FACING)), 3);
        }

        field_149934_M = false;

        if(tileentity != null){
            tileentity.validate();
            worldIn.setTileEntity(p_176446_2_, tileentity);
        }
    }

    /**
     Get the Item that this Block should drop when harvested.

     @param fortune
     the level of the Fortune enchantment on the player's tool
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return Item.getItemFromBlock(Blocks.furnace);
    }

    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        this.func_176445_e(worldIn, pos, state);
    }

    private void func_176445_e(World worldIn, BlockPos p_176445_2_, IBlockState p_176445_3_){
        if(!worldIn.isRemote){
            Block block = worldIn.getBlockState(p_176445_2_.offsetNorth()).getBlock();
            Block block1 = worldIn.getBlockState(p_176445_2_.offsetSouth()).getBlock();
            Block block2 = worldIn.getBlockState(p_176445_2_.offsetWest()).getBlock();
            Block block3 = worldIn.getBlockState(p_176445_2_.offsetEast()).getBlock();
            EnumFacing enumfacing = (EnumFacing) p_176445_3_.getValue(FACING);

            if(enumfacing == EnumFacing.NORTH && block.isFullBlock() && !block1.isFullBlock()){
                enumfacing = EnumFacing.SOUTH;
            }else if(enumfacing == EnumFacing.SOUTH && block1.isFullBlock() && !block.isFullBlock()){
                enumfacing = EnumFacing.NORTH;
            }else if(enumfacing == EnumFacing.WEST && block2.isFullBlock() && !block3.isFullBlock()){
                enumfacing = EnumFacing.EAST;
            }else if(enumfacing == EnumFacing.EAST && block3.isFullBlock() && !block2.isFullBlock()){
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(p_176445_2_, p_176445_3_.withProperty(FACING, enumfacing), 2);
        }
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
        if(this.isBurning){
            EnumFacing enumfacing = (EnumFacing) state.getValue(FACING);
            double d0 = (double) pos.getX() + 0.5D;
            double d1 = (double) pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
            double d2 = (double) pos.getZ() + 0.5D;
            double d3 = 0.52D;
            double d4 = rand.nextDouble() * 0.6D - 0.3D;

            switch(SwitchEnumFacing.field_180356_a[enumfacing.ordinal()]){
                case 1:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 - d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case 2:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d3, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                    break;
                case 3:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 - d3, 0.0D, 0.0D, 0.0D);
                    break;
                case 4:
                    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
                    worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + d4, d1, d2 + d3, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumFacing side, float hitX, float hitY, float hitZ){
        if(worldIn.isRemote){
            return true;
        }else{
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if(tileentity instanceof TileEntityExpFurnace){
                playerIn.displayGUIChest((TileEntityExpFurnace) tileentity);
            }

            return true;
        }
    }

    /**
     Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityExpFurnace();
    }

    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.func_174811_aO().getOpposite());
    }

    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.func_174811_aO().getOpposite()), 2);
    }

    public void breakBlock(World worldIn, BlockPos pos, IBlockState state){
        if(!field_149934_M){
            TileEntity tileentity = worldIn.getTileEntity(pos);

            if(tileentity instanceof TileEntityExpFurnace){
                InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityExpFurnace) tileentity);
                worldIn.updateComparatorOutputLevel(pos, this);
            }
        }

        super.breakBlock(worldIn, pos, state);
    }

    public boolean hasComparatorInputOverride(){
        return true;
    }

    public int getComparatorInputOverride(World worldIn, BlockPos pos){
        return Container.calcRedstone(worldIn.getTileEntity(pos));
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, BlockPos pos){
        return Item.getItemFromBlock(Blocks.furnace);
    }

    /**
     The type of render function that is called for this block
     */
    public int getRenderType(){
        return 3;
    }

    /**
     Possibly modify the given BlockState before rendering it on an Entity (Minecarts, Endermen, ...)
     */
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state){
        return this.getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
    }

    /**
     Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getFront(meta);

        if(enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    /**
     Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state){
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    protected BlockState createBlockState(){
        return new BlockState(this, FACING);
    }

    @SideOnly(Side.CLIENT)

    static final class SwitchEnumFacing{
        static final int[] field_180356_a = new int[EnumFacing.values().length];
        private static final String __OBFID = "CL_00002111";

        static{
            try{
                field_180356_a[EnumFacing.WEST.ordinal()] = 1;
            } catch(NoSuchFieldError var4){
            }

            try{
                field_180356_a[EnumFacing.EAST.ordinal()] = 2;
            } catch(NoSuchFieldError var3){
            }

            try{
                field_180356_a[EnumFacing.NORTH.ordinal()] = 3;
            } catch(NoSuchFieldError var2){
            }

            try{
                field_180356_a[EnumFacing.SOUTH.ordinal()] = 4;
            } catch(NoSuchFieldError var1){
            }
        }
    }
}
