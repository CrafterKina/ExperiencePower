package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityEPBase;

public class TileEntitySimpleExpInjector extends TileEntityEPBase{
    public int progressTime = 0;

    public TileEntitySimpleExpInjector(){
        super("simple_exp_injector", false, 1);
    }

    /**
     Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't this
     more of a set than a get?*
     */
    public int getInventoryStackLimit(){
        return 1;
    }
}
