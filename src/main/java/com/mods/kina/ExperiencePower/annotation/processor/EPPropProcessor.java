package com.mods.kina.ExperiencePower.annotation.processor;

import com.mods.kina.ExperiencePower.annotation.EPProp;
import com.mods.kina.ExperiencePower.config.EnumConfigCategory;
import com.mods.kina.ExperiencePower.util.ClassFinder;

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
                    epProp.category().getPropSet().add(new EnumConfigCategory.PropContainer(field, epProp.key(), epProp.defaultValve(), epProp.comment(), epProp.type()));
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
