package com.mods.kina.ExperiencePower.proxy;

import com.mods.kina.ExperiencePower.collection.EnumEPGui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler{
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        return EnumEPGui.getGuiContainer(ID).getContainer();
    }

    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z){
        return EnumEPGui.getGuiContainer(ID).getGui();
    }
}
