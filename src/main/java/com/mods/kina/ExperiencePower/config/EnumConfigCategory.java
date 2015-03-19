package com.mods.kina.ExperiencePower.config;

import com.google.common.collect.Sets;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.util.Set;

public enum EnumConfigCategory{
    GENERAL,
    COLOR,;
    Set<PropContainer> propSet;

    EnumConfigCategory(){
        propSet = Sets.newHashSet();
    }

    public Set<PropContainer> getPropSet(){
        return propSet;
    }

    public static class PropContainer{
        public final String key;
        public final Object defaultValue;
        public final String comment;
        public final Property.Type type;
        public Field insertTo;

        public PropContainer(Field insertTo, String key, Object defaultValue, String comment, Property.Type type){
            this.insertTo = insertTo;
            this.key = key;
            this.defaultValue = defaultValue;
            this.comment = comment;
            this.type = type;
        }
    }
}
