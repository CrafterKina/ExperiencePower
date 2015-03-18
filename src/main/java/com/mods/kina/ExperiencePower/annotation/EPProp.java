package com.mods.kina.ExperiencePower.annotation;

import com.mods.kina.ExperiencePower.config.EnumConfigCategory;
import net.minecraftforge.common.config.Property;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EPProp{
    EnumConfigCategory category() default EnumConfigCategory.GENERAL;

    String key();

    String defaultValve();

    String comment() default "";

    Property.Type type() default Property.Type.STRING;
}
