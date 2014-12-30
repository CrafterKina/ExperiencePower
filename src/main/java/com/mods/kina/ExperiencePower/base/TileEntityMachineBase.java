package com.mods.kina.ExperiencePower.base;

import net.minecraft.util.IChatComponent;

public abstract class TileEntityMachineBase extends TileEntityEPBase{

    public TileEntityMachineBase(IChatComponent title, int slotsCount){
        super(title, slotsCount);
    }

    public TileEntityMachineBase(String title, boolean hasCustomName, int slotsCount){
        super(title, hasCustomName, slotsCount);
    }

    //public TileEntityMachineBase(){}

    /**
     Containerにスロットを追加したりする。 やろうと思えばなんでもできるが多分スロット追加だけ。
     */
    public abstract void initContainer(ContainerMachineBase container);
}
