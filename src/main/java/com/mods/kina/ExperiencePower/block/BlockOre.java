package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockOre extends Block{
    public static final PropertyEnum TYPE = PropertyEnum.create("type", OreType.class);

    public BlockOre(){
        super(Material.rock);
        setUnlocalizedName("ore");
        setDefaultState(blockState.getBaseState().withProperty(TYPE, OreType.COPPER));
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
    }

    /**
     Gets the localized name of this block. Used for the statistics page.
     */
    public String getLocalizedName(){
        return StatCollector.translateToLocal(this.getUnlocalizedName() + ".name");
    }

    /**
     Get the Item that this Block should drop when harvested.

     @param fortune
     the level of the Fortune enchantment on the player's tool
     */
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return Item.getItemFromBlock(this);
    }

    /**
     Get the quantity dropped based on the given fortune level
     */
    public int quantityDroppedWithBonus(int fortune, Random random){
        if(fortune > 0 && Item.getItemFromBlock(this) != this.getItemDropped((IBlockState) this.getBlockState().getValidStates().iterator().next(), random, fortune)){
            int j = random.nextInt(fortune + 2) - 1;

            if(j < 0){
                j = 0;
            }

            return this.quantityDropped(random) * (j + 1);
        }else{
            return this.quantityDropped(random);
        }
    }

    @SideOnly(Side.CLIENT)
    public int getBlockColor(){
        return 0xffffff;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderColor(IBlockState state){
        switch((OreType) state.getValue(TYPE)){
            case COPPER:
                return 0x994C00;
            case TIN:
                return 0xACDBDA;
            case SILVER:
                return 0xEAE8F2;
            case WISE:
                return 0x7cfc00;
            default:
                return 0xffffff;
        }
    }

    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess worldIn, BlockPos pos, int renderPass){
        switch((OreType) worldIn.getBlockState(pos).getValue(TYPE)){
            case COPPER:
                return 0x994C00;
            case TIN:
                return 0xACDBDA;
            case SILVER:
                return 0xEAE8F2;
            case WISE:
                return 0x7cfc00;
            default:
                return 0xffffff;
        }
    }

    public int getExpDrop(IBlockAccess world, BlockPos pos, int fortune){
        IBlockState state = world.getBlockState(pos);
        Random rand = world instanceof World ? ((World) world).rand : new Random();
        if(this.getItemDropped(state, rand, fortune) != Item.getItemFromBlock(this)){
            int j;

            if(world.getBlockState(pos).getValue(TYPE) == OreType.WISE){
                j = MathHelper.getRandomIntegerInRange(rand, 5, 10);
            }else{
                j = MathHelper.getRandomIntegerInRange(rand, 0, 2);
            }

            return j;
        }
        return 0;
    }

    /**
     Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(TYPE, OreType.values()[meta]);
    }

    /**
     Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state){
        return ((OreType) state.getValue(TYPE)).ordinal();
    }

    @SuppressWarnings("unchecked")
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list){
        for(int i = 0; i < OreType.values().length; i++){
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    protected BlockState createBlockState(){
        return new BlockState(this, TYPE);
    }

    @SideOnly(Side.CLIENT)
    public EnumWorldBlockLayer getBlockLayer(){
        return EnumWorldBlockLayer.CUTOUT_MIPPED;
    }

    public enum OreType implements IStringSerializable{
        COPPER,
        TIN,
        SILVER,
        WISE;

        public String getName(){
            return name().toLowerCase();
        }
    }

}
