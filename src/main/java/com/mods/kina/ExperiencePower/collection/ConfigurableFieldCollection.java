package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.annotation.EPProp;
import net.minecraft.item.ItemDye;

import java.awt.*;

import static com.mods.kina.ExperiencePower.config.EnumConfigCategory.COLOR;

public class ConfigurableFieldCollection{
    @EPProp(category = COLOR, comment = "set color matters relating to input.")
    public static final int inputColor = Color.GREEN.getRGB();
    @EPProp(category = COLOR, comment = "set color matters relating to output.")
    public static final int outputColor = Color.RED.getRGB();
    @EPProp(category = COLOR, comment = "set colors when stained. default is Dye Color")
    public static final int[] defaultDyeColor = ItemDye.dyeColors;
    @EPProp(category = COLOR, comment = "set copper ore color")
    public static final int copperOreColor = 0x946000;
    @EPProp(category = COLOR, comment = "set tin ore color")
    public static final int tinOreColor = 0xD8F2F2;
    @EPProp(category = COLOR, comment = "set silver ore color")
    public static final int silverOreColor = 0xACDBDA;
    @EPProp(category = COLOR, comment = "set wise ore color")
    public static final int wiseOreColor = 0x49B800;
    @EPProp(category = COLOR, comment = "set iron ore color")
    public static final int ironOreColor = 0xA8A8A8;
    @EPProp(category = COLOR, comment = "set gold ore color")
    public static final int goldOreColor = 0xFFFF0B;
    @EPProp(category = COLOR, comment = "set bronze (ore) color")
    public static final int bronzeOreColor = 0xF58220;
    @EPProp(category = COLOR, comment = "set burned clay color")
    public static final int burnedClayColor = 0x3F2D18;
    @EPProp(category = COLOR, comment = "set clay color")
    public static final int clayColor = 0xA0A6B1;
}
