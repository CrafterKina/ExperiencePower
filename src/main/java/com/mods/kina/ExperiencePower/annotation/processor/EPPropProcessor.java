package com.mods.kina.ExperiencePower.annotation.processor;

import com.google.common.primitives.Primitives;
import com.mods.kina.ExperiencePower.annotation.EPProp;
import com.mods.kina.ExperiencePower.config.EnumConfigCategory;
import com.mods.kina.ExperiencePower.util.ClassFinder;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.util.List;

public class EPPropProcessor{
    public static final EPPropProcessor instance = new EPPropProcessor();

    public void process(){
        try{
            List<Class<?>> classList = ClassFinder.instance.findClasses("com.mods.kina.ExperiencePower");
            for(Class<?> clazz : classList){
                for(Field field : clazz.getFields()){
                    EPProp epProp = field.getAnnotation(EPProp.class);
                    if(epProp == null) continue;
                    String key = epProp.key().isEmpty() ? field.getName() : epProp.key();
                    Property.Type type = getType(field);
                    Object defaultValve = epProp.defaultValve().isEmpty() ? field.get(null) : epProp.defaultValve();
                    epProp.category().getPropSet().add(new EnumConfigCategory.PropContainer(field, key, defaultValve, epProp.comment(), type));
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private Property.Type getType(Field field) throws IllegalAccessException{
        if(Primitives.wrap(field.getType()) == Integer.class){
            return Property.Type.INTEGER;
        }
        if(Primitives.wrap(field.getType()) == Boolean.class){
            return Property.Type.BOOLEAN;
        }

        if(Primitives.wrap(field.getType()) == Double.class){
            return Property.Type.DOUBLE;
        }

        if(Primitives.wrap(field.getType()).isArray()){
            if(Primitives.wrap(field.getType()).getComponentType() == int.class){
                return Property.Type.INTEGER;
            }
            if(Primitives.wrap(field.getType()).getComponentType() == boolean.class){
                return Property.Type.BOOLEAN;
            }

            if(Primitives.wrap(field.getType()).getComponentType() == double.class){
                return Property.Type.DOUBLE;
            }
        }

        return Property.Type.STRING;
    }
}
