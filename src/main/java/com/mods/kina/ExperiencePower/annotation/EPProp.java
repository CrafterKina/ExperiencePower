package com.mods.kina.ExperiencePower.annotation;

import com.mods.kina.ExperiencePower.config.EnumConfigCategory;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EPProp{
    EnumConfigCategory category() default EnumConfigCategory.GENERAL;

    String key() default "";

    String defaultValve() default "";

    String comment() default "";
}
