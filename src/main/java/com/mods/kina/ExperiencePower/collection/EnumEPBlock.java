package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.base.BlockEPBase;
import com.mods.kina.ExperiencePower.block.*;
import com.mods.kina.ExperiencePower.item.ItemOre;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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
    Ore(new BlockOre(), ItemOre.class),
    //SimpleExperienceInjector(new BlockSimpleExpInjector()),
    ItemPipe(new BlockBasicPipe()),
    MachineCore(new BlockMachineCore()),
    Workbench(new BlockEPWorkbench()),;

    private Class<? extends ItemBlock> itemBlock;
    private Block block;
    private Object[] itemConstructorsArgs;

    EnumEPBlock(Block block){
        this(block, ItemBlock.class);
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock){
        this(block, itemBlock, 1, new Object[0]);
    }

    EnumEPBlock(Block block, Class<? extends ItemBlock> itemBlock, Object... itemCtorArgs){
        this.block = block;
        this.itemBlock = itemBlock;
        itemConstructorsArgs = itemCtorArgs;
    }

    public Block getBlock(){
        return block;
    }

    public Class<? extends ItemBlock> getItemBlock(){
        return itemBlock;
    }

    public String getBlockName(){
        return block.getUnlocalizedName().replaceFirst("tile\\.", "").replaceFirst("kina\\.", "");
    }

    public Object[] getItemConstructorsArgs(){
        return itemConstructorsArgs;
    }

    public ItemMeshDefinition getMeshDef(){
        if(block instanceof BlockEPBase){
            return ((BlockEPBase) block).getMeshDef();
        }

        return new ItemMeshDefinition(){
            public ModelResourceLocation getModelLocation(ItemStack stack){
                return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + getBlockName(), "inventory");
            }
        };
    }
}