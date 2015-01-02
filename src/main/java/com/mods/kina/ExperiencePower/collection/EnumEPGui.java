package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.base.GuiMachineBase;
import com.mods.kina.ExperiencePower.container.ContainerExpFurnace;
import com.mods.kina.ExperiencePower.gui.GuiExpFurnace;
import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;

public enum EnumEPGui{
    ExperienceAbsorber,
    ExperienceDischarger,
    ExperienceFurnace(ContainerExpFurnace.class, GuiExpFurnace.class),;

    private Class<? extends Container> container;
    private Class<? extends Gui> gui;

    EnumEPGui(Class<? extends Container> container, Class<? extends Gui> gui){
        this.container = container;
        this.gui = gui;
    }

    EnumEPGui(){
        this(ContainerMachineBase.class, GuiMachineBase.class);
    }

    public static EnumEPGui getGuiContainer(int id){
        EnumEPGui[] epGui = EnumEPGui.values();
        if(epGui.length < id) return null;
        else return epGui[id];
    }

    public Class<? extends Container> getContainer(){
        return container;
    }

    public Class<? extends Gui> getGui(){
        return gui;
    }
}
