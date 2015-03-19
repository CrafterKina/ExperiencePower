package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.block.*;
import com.mods.kina.ExperiencePower.item.ItemOre;
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
    //ExperienceFurnace(new BlockExpFurnace()),
    Ore(new BlockOre(), ItemOre.class, "kina_experiencepower:ore"),
    //SimpleExperienceInjector(new BlockSimpleExpInjector()),
    ItemPipe(new BlockBasicPipe()),
    MachineCore(new BlockMachineCore()),
    Workbench(new BlockEPWorkbench()),;

    private Class<? extends ItemBlock> itemBlock;
    private Block block;
    private int modelCount;
    private Object[] itemConstructorsArgs;
    private String[] modelNames;

    EnumEPBlock(Block block){
        this(block, ItemBlock.class);
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock){
        this(block, itemBlock, 1);
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock, int modelCount){
        this(block, itemBlock, modelCount, new Object[0]);
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock, int modelCount, Object... itemCtorArgs){
        this.block = block;
        this.itemBlock = itemBlock;
        this.modelCount = modelCount;
        itemConstructorsArgs = itemCtorArgs;
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock, String... modelNames){
        this(block, itemBlock, modelNames.length);
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

    public Object[] getItemConstructorsArgs(){
        return itemConstructorsArgs;
    }
}