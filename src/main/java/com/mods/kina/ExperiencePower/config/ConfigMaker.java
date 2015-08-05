package com.mods.kina.ExperiencePower.config;

import com.google.common.primitives.Primitives;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.io.Serializable;
import java.lang.reflect.Array;

public class ConfigMaker{
    public static void createConfig(FMLPreInitializationEvent event){
        Configuration config = new Configuration(new File(event.getModConfigurationDirectory() + "/" + StaticFieldCollection.MODID.split("_")[0], StaticFieldCollection.MODID.split("_")[1] + ".cfg"));
        try{
            config.load();
            for(EnumConfigCategory cc : EnumConfigCategory.values())
                for(EnumConfigCategory.PropContainer pc : cc.propSet){
                    pc.insertTo.setAccessible(true);
                    Property property;
                    if(pc.defaultValue.getClass().isArray()){
                        pc.insertTo.set(null, getInsertObject(config.get(cc.toString(), pc.key, arrayToString(pc.defaultValue), pc.comment, pc.type), pc.type));
                    }else{
                        property = config.get(cc.toString(), pc.key, primitiveToString(pc.defaultValue), pc.comment, pc.type);
                        switch(pc.type){
                            case INTEGER:
                                pc.insertTo.setInt(null, property.getInt());
                                break;
                            case BOOLEAN:
                                pc.insertTo.setBoolean(null, property.getBoolean());
                                break;
                            case DOUBLE:
                                pc.insertTo.setDouble(null, property.getDouble());
                                break;
                            default:
                                pc.insertTo.set(null, property.getString());
                                break;
                        }
                    }
                }
        } catch(Exception e){
            FMLLog.severe(e.getLocalizedMessage());
        }finally{
            config.save();
        }
    }

    //第一引数から得られるTypeはすべてStringっぽいので、オリジナルのTypeも渡す。
    private static Serializable getInsertObject(Property property, Property.Type type){
        switch(type){
            case INTEGER:
                return property.isList() ? property.getIntList() : property.getInt();
            case BOOLEAN:
                return property.isList() ? property.getBooleanList() : property.getBoolean();
            case DOUBLE:
                return property.isList() ? property.getDoubleList() : property.getDouble();
            default:
                return property.isList() ? property.getStringList() : property.getString();
        }
    }

    //Forgeの処理を切り出しただけ。
    private static String[] arrayToString(Object obj){
        String[] values = new String[Array.getLength(obj)];
        for(int i = 0; i < Array.getLength(obj); i++){
            values[i] = primitiveToString(Array.get(obj, i));
        }
        return values;
    }

    //Guavaは神
    private static String primitiveToString(Object obj){
        if(Primitives.wrap(obj.getClass()) == Integer.class){
            return Integer.toString((Integer) obj);
        }

        if(Primitives.wrap(obj.getClass()) == Boolean.class){
            return Boolean.toString((Boolean) obj);
        }

        if(Primitives.wrap(obj.getClass()) == Double.class){
            return Double.toString((Double) obj);
        }
        return obj.toString();
    }
}
