package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.container.ContainerEPWorkbench;
import com.mods.kina.ExperiencePower.container.ContainerRotaryMachine;
import com.mods.kina.ExperiencePower.gui.GuiEPWorkbench;
import com.mods.kina.ExperiencePower.gui.GuiRotaryMachine;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public enum EnumEPGuiContainer{
    Workbench(ContainerEPWorkbench.class, GuiEPWorkbench.class),
    RotaryMachine(ContainerRotaryMachine.class, GuiRotaryMachine.class);
    //ExperienceFurnace(ContainerExpFurnace.class, GuiExpFurnace.class),;

    private Class<? extends Container> container;
    private Class<? extends Gui> gui;

    EnumEPGuiContainer(Class<? extends Container> container, Class<? extends Gui> gui){
        this.container = container;
        this.gui = gui;
    }

    public static EnumEPGuiContainer getGuiContainer(int id){
        EnumEPGuiContainer[] epGui = EnumEPGuiContainer.values();
        if(epGui.length < id) return null;
        else return epGui[id];
    }

    public void openGui(World world, BlockPos pos, EntityPlayer player){
        player.openGui(StaticFieldCollection.epCore, ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
    }

    public Class<? extends Container> getContainer(){
        return container;
    }

    public Class<? extends Gui> getGui(){
        return gui;
    }
}
