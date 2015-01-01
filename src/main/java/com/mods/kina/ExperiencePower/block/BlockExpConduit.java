/*
package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockConduitBase;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;

*/
/**
 Exp導管。
 *//*

public class BlockExpConduit extends BlockConduitBase{
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 30000);
    public BlockExpConduit(){
        super();
        setDefaultState(getDefaultState().withProperty(POWER,0));
    }

    */
/**
 @param facing このブロックがある方向
 *//*

    public static int distribute(EnumFacing facing, IBlockState state){
        */
/*for(int i = 0; i < INPUT.length; i++){
            if((Boolean)state.getValue(INPUT[i]))distribute(EnumFacing.values()[i],)1000 / getTotalSupply(state);
        }*//*

        return (Integer)state.getValue(POWER)*(Integer)state.getValue(OUTPUT[facing.getOpposite().ordinal()])/getTotalDemand(state);
    }

    public static boolean addContents(IBlockState state, int ml){
        for(PropertyInteger integer:OUTPUT){
            if((Integer)state.getValue(integer)> 0){
                ml/getTotalDemand(state);
            }
        }
    }
}*/
