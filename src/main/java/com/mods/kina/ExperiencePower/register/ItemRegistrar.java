package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemRegistrar{
    public static void registerItem(){
        for(EnumEPItem epItem: EnumEPItem.values()){
            GameRegistry.registerItem(epItem.getItem(), epItem.getItem().getUnlocalizedName().substring(5));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(){
        for(EnumEPItem epItem:EnumEPItem.values()){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(epItem.getItem(), 0, new ModelResourceLocation(StaticFieldCollection.MODID + ":" + epItem.getItem().getUnlocalizedName().substring(5), "inventory"));
        }
    }
}
