package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.IWrenchable;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public class BlockBasicPipe extends Block implements IWrenchable{
    public static final PropertyEnum in = PropertyEnum.create("in", optionalFacing.class);
    public static final PropertyEnum out = PropertyEnum.create("out", optionalFacing.class);

    public BlockBasicPipe(){
        super(Material.rock);
        setUnlocalizedName("item_pipe");
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        setDefaultState(blockState.getBaseState().withProperty(in, optionalFacing.NONE).withProperty(out, optionalFacing.NONE));
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

    public static void setFace(IBlockState state, IProperty property, optionalFacing face){
        state.withProperty(property, face);
    }

    public boolean wrench(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        /*IBlockState state=worldIn.getBlockState(pos);
        if(!playerIn.isSneaking()){
            if(isNone(state, in) || getFace(state, in) != side) setFace(state, in, convertToOptional(side));
            else setFace(state, in, optionalFacing.NONE);
        }else{
            if(isNone(state, out) || getFace(state, out) != side) setFace(state, out, convertToOptional(side));
            else setFace(state, out, optionalFacing.NONE);
        }*/
        worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(in, optionalFacing.NONE).withProperty(out, optionalFacing.WEST));
        playerIn.addChatComponentMessage(new ChatComponentText(worldIn.getBlockState(pos).getValue(in).toString() + worldIn.getBlockState(pos).getValue(out).toString()));
        return true;
    }

    /**
     Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(in, optionalFacing.NONE).withProperty(out, optionalFacing.NONE);
    }

    /**
     Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state){
        return 0;
    }

    protected BlockState createBlockState(){
        return new BlockState(this, in, out);
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
