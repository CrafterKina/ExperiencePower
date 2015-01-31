package com.mods.kina.ExperiencePower.util;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class PlayerNBTIO{
    public static final PlayerNBTIO instance = new PlayerNBTIO();
    private Minecraft mc = Minecraft.getMinecraft();
    private EntityPlayer player = mc.thePlayer;

    protected PlayerNBTIO(){}

    public NBTTagCompound getNBT(){
        return player.getEntityData();
    }

    public void setNBTTag(NBTBase nbt, String tagName){
        getNBT().setTag(tagName, nbt);
    }

    public NBTBase getNBTTag(String tagName){
        return getNBT().getTag(tagName);
    }
}
