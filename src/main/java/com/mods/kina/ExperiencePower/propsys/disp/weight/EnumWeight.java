package com.mods.kina.ExperiencePower.propsys.disp.weight;

import com.mods.kina.ExperiencePower.propsys.ItemStackProperties;
import net.minecraft.item.ItemStack;

public enum EnumWeight{
    Tier0{
        public String getTierString(float value){
            //todo randomize
            if(value<0.4) return "Lightly";
            if(value>0.6) return "Heavily";
            return "Normally";
        }
    },
    Tier1{
        private ItemStack base;

        public void setNeedValues(Object... args){
            if(args==null|| !(args[0]instanceof ItemStack))throw new IllegalArgumentException();
            base= (ItemStack) args[0];
        }

        public String getTierString(float value){
            if(getWeight(base)>value)return "Lighter than "+base.getDisplayName();
            if(getWeight(base)<value)return "Heavier than "+base.getDisplayName();
            return "Just "+base.getDisplayName();
        }
    },
    Tier2{
        public String getTierString(float value){
            return null;
        }
    },
    Tier3{
        public String getTierString(float value){
            return null;
        }
    },
    Tier4{
        public String getTierString(float value){
            return null;
        }
    },;

    public abstract String getTierString(float value);
    public void setNeedValues(Object... args){

    }
    protected float getWeight(ItemStack stack){
        return ItemStackProperties.instance.getPropertyContainer(stack).getWeight();
    }
}
