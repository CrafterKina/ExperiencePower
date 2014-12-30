package com.mods.kina.ExperiencePower.block;

import com.mods.kina.ExperiencePower.base.BlockConduitBase;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;

/**
 Exp導管。
 */
public class BlockExpConduit extends BlockConduitBase{
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 30000);
    public BlockExpConduit(){
        super();
    }

    public static boolean addContents(IBlockState state, int ml){
        if((Integer) state.getValue(POWER) + ml > 30000) return false;
        else state.withProperty(POWER, (Integer) state.getValue(POWER) + ml);
        return true;
    }
}