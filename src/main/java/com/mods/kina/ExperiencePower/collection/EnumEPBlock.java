package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.block.BlockBlowFan;
import com.mods.kina.ExperiencePower.block.BlockExpAbsorber;
import com.mods.kina.ExperiencePower.block.BlockExpDischarger;
import net.minecraft.block.Block;

/**
 ExperiencePowerのBlockをひたすら列挙するクラス。
 */
public enum EnumEPBlock{
    /*ExperienceConduit(new BlockExpConduit()),*/
    ExperienceAbsorber(new BlockExpAbsorber()),
    ExperienceDischarger(new BlockExpDischarger()),
    BlowFan(new BlockBlowFan()),;

    private Block block;

    EnumEPBlock(Block block){
        this.block=block;
    }

    public Block getBlock(){
        return block;
    }
}