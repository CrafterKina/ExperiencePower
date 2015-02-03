package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.invent.InventionElement;
import net.minecraft.init.Items;

public enum EnumEPInvention{
    Sword(new InventionElement("achievement.openInventory", "openInventory", 0, 0, Items.book));
    /** Is the smallest column used to display a achievement on the GUI. */
    public static int minDisplayColumn;
    /** Is the smallest row used to display a achievement on the GUI. */
    public static int minDisplayRow;
    /** Is the biggest column used to display a achievement on the GUI. */
    public static int maxDisplayColumn;
    /** Is the biggest row used to display a achievement on the GUI. */
    public static int maxDisplayRow;
    private InventionElement inventionElement;

    EnumEPInvention(InventionElement inventionElement){
        this.inventionElement = inventionElement;
    }

    public InventionElement getInventionElement(){
        return inventionElement;
    }
}
