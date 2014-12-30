package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;

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
    public static final PropertyInteger DOWN = PropertyInteger.create("down", 0, 12);
    public static final PropertyInteger UP = PropertyInteger.create("up", 0, 12);
    public static final PropertyInteger NORTH = PropertyInteger.create("north", 0, 12);
    public static final PropertyInteger SOUTH = PropertyInteger.create("south", 0, 12);
    public static final PropertyInteger WEST = PropertyInteger.create("west", 0, 12);
    public static final PropertyInteger EAST = PropertyInteger.create("east", 0, 12);
    public static final PropertyBool D = PropertyBool.create("d");
    public static final PropertyBool U = PropertyBool.create("u");
    public static final PropertyBool N = PropertyBool.create("n");
    public static final PropertyBool S = PropertyBool.create("s");
    public static final PropertyBool W = PropertyBool.create("w");
    public static final PropertyBool E = PropertyBool.create("e");

    public BlockConduitBase(){
        super(Material.rock);
        setCreativeTab(EnumEPCreativeTab.BLOCK.getCreativeTab());
        //setDefaultState(blockState.getBaseState().withProperty(DOWN,NOT_CONNECTION).withProperty(UP,NOT_CONNECTION).withProperty(NORTH,NOT_CONNECTION).withProperty(SOUTH,NOT_CONNECTION).withProperty(WEST,NOT_CONNECTION).withProperty(EAST,NOT_CONNECTION));
        setDefaultState(blockState.getBaseState().withProperty(DOWN, 0).withProperty(UP, 0).withProperty(NORTH, 0).withProperty(SOUTH, 0).withProperty(WEST, 0).withProperty(EAST, 0).withProperty(D, false).withProperty(U, false).withProperty(N, false).withProperty(S, false).withProperty(W, false).withProperty(E, false));
    }

    public static boolean addContents(IBlockState state, int valve){
        return false;
    }
}