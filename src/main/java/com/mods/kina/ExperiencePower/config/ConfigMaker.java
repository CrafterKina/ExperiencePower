package com.mods.kina.ExperiencePower.config;

import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public class ConfigMaker{
    public static void createConfig(FMLPreInitializationEvent event){
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory() + "/" + StaticFieldCollection.MODID.split("_")[0], StaticFieldCollection.MODID.split("_")[1] + ".cfg"));
        try{
            config.load();
            for(EnumConfigCategory cc : EnumConfigCategory.values())
                for(EnumConfigCategory.PropContainer pc : cc.propSet){
                    pc.insertTo.set(null, config.get(cc.toString(), pc.key, pc.defaultValue, pc.comment, pc.type).getString());
                }
        } catch(Exception e){
            FMLLog.severe(e.getLocalizedMessage());
        }finally{
            config.save();
        }
    }
}
