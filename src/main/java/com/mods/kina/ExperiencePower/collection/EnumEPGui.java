package com.mods.kina.ExperiencePower.collection;

import net.minecraft.client.gui.Gui;
import net.minecraft.inventory.Container;

public enum EnumEPGui{
    ;

    private Container container;
    private Gui gui;

    EnumEPGui(Container container, Gui gui){
        this.container = container;
        this.gui = gui;
    }

    public static EnumEPGui getGuiContainer(int id){
        EnumEPGui[] epGui = EnumEPGui.values();
        if(epGui.length < id) return null;
        else return epGui[id];
    }

    public Container getContainer(){
        return container;
    }

    public Gui getGui(){
        return gui;
    }
}
