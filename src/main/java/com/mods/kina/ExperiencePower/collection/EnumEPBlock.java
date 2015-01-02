package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.block.*;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

/**
 ExperiencePowerのBlockをひたすら列挙するクラス。
 */
public enum EnumEPBlock{
    /*ExperienceConduit(new BlockExpConduit()),*/
    ExperienceAbsorber(new BlockExpAbsorber()),
    ExperienceDischarger(new BlockExpDischarger()),
    BlowFan(new BlockBlowFan()),
    TrainingBarrel(new BlockTrainingBarrel()),
    ExperienceCrop(new BlockExpWheat()),
    ExperienceFurnace(new BlockExpFurnace());

    private Class<? extends ItemBlock> itemBlock;
    private Block block;
    private int modelCount;
    private String[] modelNames;

    EnumEPBlock(Block block){
        this.block=block;
        itemBlock = ItemBlock.class;
        modelCount = 1;
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock){
        this(block);
        this.itemBlock = itemBlock;
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock, int modelCount){
        this(block, itemBlock);
        this.modelCount = modelCount;
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock, String... modelNames){
        this(block, itemBlock);
        modelCount = modelNames.length;
        this.modelNames = modelNames;
    }

    public Block getBlock(){
        return block;
    }

    public Class<? extends ItemBlock> getItemBlock(){
        return itemBlock;
    }

    public int getModelCount(){
        return modelCount;
    }

    public String getBlockName(){
        return block.getUnlocalizedName().substring(5);
    }

    public String[] getModelNames(){
        return modelNames;
    }
}