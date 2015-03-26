package com.mods.kina.ExperiencePower.plugin;

import com.mods.kina.ExperiencePower.plugin.nei.PluginNEICore;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PluginLoader{
    public static void load(){
        try{
            commonLoad();
            clientLoad();
        } catch(Exception e){
            e.printStackTrace(System.err);
        }
    }

    @SideOnly(Side.CLIENT)
    private static void clientLoad(){
        if(Loader.isModLoaded("NotEnoughItems")) PluginNEICore.load();
    }

    private static void commonLoad(){

    }
}
