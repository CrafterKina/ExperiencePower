package com.mods.kina.ExperiencePower.config;

import com.google.common.collect.Sets;
import net.minecraftforge.common.config.Property;

import java.lang.reflect.Field;
import java.util.Set;

public enum EnumConfigCategory{
    GENERAL,;
    Set<PropContainer> propSet;

    EnumConfigCategory(){
        propSet = Sets.newHashSet();
    }

    public Set<PropContainer> getPropSet(){
        return propSet;
    }

    public static class PropContainer{
        public final String key;
        public final String defaultValue;
        public final String comment;
        public final Property.Type type;
        public Field insertTo;

        public PropContainer(Field insertTo, String key, String defaultValue, String comment, Property.Type type){
            this.insertTo = insertTo;
            this.key = key;
            this.defaultValue = defaultValue;
            this.comment = comment;
            this.type = type;
        }
    }
}
