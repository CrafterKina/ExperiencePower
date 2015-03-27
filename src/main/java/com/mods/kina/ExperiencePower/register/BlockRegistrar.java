package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegistrar{
    //EnumからBlockを取得、登録している。
    public static void registerBlock(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            GameRegistry.registerBlock(epBlock.getBlock(), epBlock.getItemBlock(), epBlock.getBlockName(), epBlock.getItemConstructorsArgs());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(epBlock.getBlock()),epBlock.getMeshDef());
        }
    }
}
