package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityEPBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.gui.IUpdatePlayerListBox;

public class TileEntitySimpleExpInjector extends TileEntityEPBase implements IUpdatePlayerListBox{
    public int progressTime = 0;
    public boolean triggered = false;

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

    protected void readFromNBTExtended(NBTTagCompound compound){
        progressTime = compound.getInteger("progressTime");
    }

    protected void writeToNBTExtended(NBTTagCompound compound){
        compound.setInteger("progressTime", progressTime);
    }

    /**
     Updates the JList with a new model.
     */
    public void update(){
        if(triggered || progressTime > 0){
            if(progressTime == 0) progressTime = 300;
            progressTime--;
            triggered = false;
        }
    }
}
