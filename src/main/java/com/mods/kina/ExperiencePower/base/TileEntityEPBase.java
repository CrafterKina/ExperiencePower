package com.mods.kina.ExperiencePower.base;

import net.minecraft.util.IChatComponent;

public abstract class TileEntityEPBase extends IInventoryImpl{

    public TileEntityEPBase(String title, boolean hasCustomName, int slotsCount){
        super(title, hasCustomName, slotsCount);
    }

    public TileEntityEPBase(IChatComponent title, int slotsCount){
        super(title, slotsCount);
    }

    //public TileEntityEPBase(){}
}
