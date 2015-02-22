package com.mods.kina.ExperiencePower.collection;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

public enum EnumEPAchievement{
    NewOres(new Achievement("kina.ep.achievement.newOres", "newOres", 0, 0, EnumEPBlock.Ore.getBlock(), null).setIndependent()),
    ChunkOfKnowledge(new Achievement("kina.ep.achievement.chunkOfKnowledge", "chunkOfKnowledge", 2, 1, new ItemStack(EnumEPBlock.Ore.getBlock(), 1, 3), NewOres.getAchievement()));
    private Achievement achievement;

    EnumEPAchievement(Achievement achievement){
        this.achievement = achievement;
    }

    public Achievement getAchievement(){
        return achievement;
    }
}
