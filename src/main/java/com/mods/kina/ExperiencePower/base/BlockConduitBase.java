package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

/**
 導管。
 */
public abstract class BlockConduitBase extends Block{
    //public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
    /*public static final PropertyEnum DOWN = PropertyEnum.create("down", EnumIO.class);
    public static final PropertyEnum UP = PropertyEnum.create("up", EnumIO.class);
    public static final PropertyEnum NORTH = PropertyEnum.create("north", EnumIO.class);
    public static final PropertyEnum SOUTH = PropertyEnum.create("south", EnumIO.class);
    public static final PropertyEnum WEST = PropertyEnum.create("west", EnumIO.class);
    public static final PropertyEnum EAST = PropertyEnum.create("east", EnumIO.class);*/
    public static final PropertyBool[] INPUT = new PropertyBool[]{PropertyBool.create("d_i"), PropertyBool.create("u_i"), PropertyBool.create("n_i"), PropertyBool.create("s_i"), PropertyBool.create("w_i"), PropertyBool.create("e_i")};
    public static final PropertyInteger[] OUTPUT = new PropertyInteger[]{PropertyInteger.create("d_o", 0, 6), PropertyInteger.create("u_o", 0, 6), PropertyInteger.create("n_o", 0, 6), PropertyInteger.create("s_o", 0, 6), PropertyInteger.create("w_o", 0, 6), PropertyInteger.create("e_o", 0, 6)};
    public static final PropertyBool[] CONNECTION = new PropertyBool[]{PropertyBool.create("d"), PropertyBool.create("u"), PropertyBool.create("n"), PropertyBool.create("s"), PropertyBool.create("w"), PropertyBool.create("e")};

    public BlockConduitBase(){
        super(Material.rock);
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        //setDefaultState(blockState.getBaseState().withProperty(DOWN,NOT_CONNECTION).withProperty(UP,NOT_CONNECTION).withProperty(NORTH,NOT_CONNECTION).withProperty(SOUTH,NOT_CONNECTION).withProperty(WEST,NOT_CONNECTION).withProperty(EAST,NOT_CONNECTION));
        setDefaultState(blockState.getBaseState().withProperty(INPUT[0], 0).withProperty(INPUT[1], 0).withProperty(INPUT[2], 0).withProperty(INPUT[3], 0).withProperty(INPUT[4], 0).withProperty(INPUT[5], 0).withProperty(OUTPUT[0], 0).withProperty(OUTPUT[1], 0).withProperty(OUTPUT[2], 0).withProperty(OUTPUT[3], 0).withProperty(OUTPUT[4], 0).withProperty(OUTPUT[5], 0).withProperty(CONNECTION[0], false).withProperty(CONNECTION[1], false).withProperty(CONNECTION[2], false).withProperty(CONNECTION[3], false).withProperty(CONNECTION[4], false).withProperty(CONNECTION[5], false));
    }

    /**
     @param facing
     このブロックがある方向
     */
    public static int distribute(EnumFacing facing, IBlockState state){
        return 0;
    }

    public static int getTotalSupply(IBlockState state){
        int total = 0;
        for(PropertyBool propBool : INPUT){
            total += (Boolean) state.getValue(propBool) ? 1 : 0;
        }
        return total;
    }

    public static int getTotalDemand(IBlockState state){
        int total = 0;
        for(PropertyInteger propInt : OUTPUT){
            total += ((Integer) state.getValue(propInt));
        }
        return total;
    }

    public static boolean addContents(IBlockState state, int valve){
        return false;
    }
}