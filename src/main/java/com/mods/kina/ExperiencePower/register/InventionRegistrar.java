package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPInvention;
import com.mods.kina.ExperiencePower.collection.EnumEPInventionPage;
import com.mods.kina.ExperiencePower.invent.InventionPage;
import net.minecraftforge.common.util.EnumHelper;

public class InventionRegistrar{
    public static void registerInvention(){
        for(EnumEPInvention invention : EnumEPInvention.values()){
            invention.getInventionElement().getPage().getPage().elementList.add(invention.getInventionElement());
        }
    }

    public static EnumEPInventionPage addPage(String name, InventionPage... page){
        return EnumHelper.addEnum(EnumEPInventionPage.class, name, page);
    }
}
