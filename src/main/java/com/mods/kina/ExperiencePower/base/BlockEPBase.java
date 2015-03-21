package com.mods.kina.ExperiencePower.base;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockEPBase extends Block{
    public BlockEPBase(Material materialIn){
        super(materialIn);
    }

    public BlockEPBase(){
        this(Material.rock);
    }

    @Override
    public String getUnlocalizedName(){
        return super.getUnlocalizedName().replaceFirst("tile\\.", "kina.tile.");
    }
}
