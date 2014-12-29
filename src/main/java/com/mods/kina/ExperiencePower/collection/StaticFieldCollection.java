package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.ExperiencePowerCore;
import com.mods.kina.ExperiencePower.event.handler.EventHandler;

/**
 静的なフィールドはここに突っ込みたい。
 使わないかもしれない。
 */
public class StaticFieldCollection{
    public static final String MODID="kina_ExperiencePower";
    public static ExperiencePowerCore epCore=ExperiencePowerCore.core;
    public static EventHandler handler=new EventHandler();
}
