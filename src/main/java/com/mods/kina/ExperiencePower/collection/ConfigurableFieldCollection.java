package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.annotation.EPProp;
import net.minecraft.item.ItemDye;

import java.awt.*;

import static com.mods.kina.ExperiencePower.config.EnumConfigCategory.COLOR;

public class ConfigurableFieldCollection{
    @EPProp(category = COLOR, comment = "set color matters relating to input.")
    public static int inputColor = Color.GREEN.getRGB();
    @EPProp(category = COLOR, comment = "set color matters relating to output.")
    public static int outputColor = Color.RED.getRGB();
    @EPProp(category = COLOR, comment = "set colors when stained. default is Dye Color")
    public static int[] defaultDyeColor = ItemDye.dyeColors;
    @EPProp(category = COLOR, comment = "set copper ore color")
    public static int copperOreColor = 0x946000;
    @EPProp(category = COLOR, comment = "set tin ore color")
    public static int tinOreColor = 0xD8F2F2;
    @EPProp(category = COLOR, comment = "set silver ore color")
    public static int silverOreColor = 0xACDBDA;
    @EPProp(category = COLOR, comment = "set wise ore color")
    public static int wiseOreColor = 0x49B800;
}
