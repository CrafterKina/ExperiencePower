package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.block.*;
import net.minecraft.block.Block;

/**
 ExperiencePowerのBlockをひたすら列挙するクラス。
 */
public enum EnumEPBlock{
    /*ExperienceConduit(new BlockExpConduit()),*/
    ExperienceAbsorber(new BlockExpAbsorber()),
    ExperienceDischarger(new BlockExpDischarger()),
    BlowFan(new BlockBlowFan()),
    TrainingBarrel(new BlockTrainingBarrel()),
    ExperienceCrop(new BlockExpWheat()),;

    private Block block;

    EnumEPBlock(Block block){
        this.block=block;
    }

    public Block getBlock(){
        return block;
    }
}