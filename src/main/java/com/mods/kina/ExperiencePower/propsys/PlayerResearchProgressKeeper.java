package com.mods.kina.ExperiencePower.propsys;

import com.google.common.collect.Lists;
import com.mods.kina.ExperiencePower.util.PlayerNBTIO;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;

import java.util.List;

public class PlayerResearchProgressKeeper{
    public static final PlayerResearchProgressKeeper keeper = new PlayerResearchProgressKeeper();

    private PlayerResearchProgressKeeper(){}

    public void writeToNBT(List<ProgressContainer> list){
        NBTTagList tierList = new NBTTagList();
        NBTTagList compoundList = new NBTTagList();
        for(int i = 0; i < list.size(); i++){
            compoundList.set(i, list.get(i).compound);
            tierList.set(i, new NBTTagInt(list.get(i).tier));
        }
        PlayerNBTIO.instance.setNBTTag("ep_progress_valve", tierList);
        PlayerNBTIO.instance.setNBTTag("ep_progress_key", compoundList);
    }

    public List<ProgressContainer> readFromNBT(){
        NBTTagList tierList = (NBTTagList) PlayerNBTIO.instance.getNBTTag("ep_progress_valve");
        NBTTagList compoundList = (NBTTagList) PlayerNBTIO.instance.getNBTTag("ep_progress_key");
        List<ProgressContainer> result = Lists.newArrayList();
        for(int i = 0; i < tierList.tagCount(); i++){
            result.set(i, new ProgressContainer(((NBTTagCompound) compoundList.get(i)), ((NBTTagInt) tierList.get(i)).getInt()));
        }
        return result;
    }

    public static class ProgressContainer{
        private final NBTTagCompound compound;
        private final int tier;

        public ProgressContainer(NBTTagCompound compound, int tier){
            this.compound = compound;
            this.tier = tier;
        }

        public NBTTagCompound getCompound(){
            return compound;
        }

        public int getTier(){
            return tier;
        }
    }
}
