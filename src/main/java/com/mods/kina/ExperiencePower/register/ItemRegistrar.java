package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRegistrar{
    public static void registerItem(){
        for(EnumEPItem epItem : EnumEPItem.values()){
            GameRegistry.registerItem(epItem.getItem(), epItem.getItem().getUnlocalizedName().replaceFirst("item\\.", "").replaceFirst("kina\\.", ""));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(){
        for(EnumEPItem epItem : EnumEPItem.values()){
            ModelLoader.setCustomMeshDefinition(epItem.getItem(), epItem.getMeshDef());
        }
    }
}
