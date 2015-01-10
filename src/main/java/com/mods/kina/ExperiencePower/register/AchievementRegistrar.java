package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPAchievement;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;

public class AchievementRegistrar{
    public static void registerAchievement(){
        Achievement[] achievements = new Achievement[EnumEPAchievement.values().length];
        for(int i = 0; i < EnumEPAchievement.values().length; i++){
            achievements[i] = EnumEPAchievement.values()[i].getAchievement();
        }
        AchievementPage.registerAchievementPage(new AchievementPage("ExperiencePower", achievements));
    }
}
