package com.mods.kina.ExperiencePower.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;

/**
 導管となる......はずだった。
 */
public class BlockExpConduit extends Block{
    public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
    public BlockExpConduit(){
        super(Material.rock);
    }
}