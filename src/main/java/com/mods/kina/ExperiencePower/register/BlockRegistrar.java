package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegistrar{

    public static void registerBlock(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            GameRegistry.registerBlock(epBlock.getBlock(), epBlock.getItemBlock(), epBlock.getBlockName(), epBlock.getItemConstructorsArgs());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            if(epBlock.getModelCount() == 1){
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), 0, new ModelResourceLocation(StaticFieldCollection.MODID + ":" + epBlock.getBlockName(), "inventory"));
            }else{
                if(epBlock.getModelNames() != null && epBlock.getModelNames().length > 0){
                    ModelBakery.addVariantName(Item.getItemFromBlock(epBlock.getBlock()), epBlock.getModelNames());
                    for(int i = 0; i < epBlock.getModelCount(); i++){
                        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), i, new ModelResourceLocation(epBlock.getModelNames()[i], "inventory"));
                    }
                }else{
                    for(int i = 0; i < epBlock.getModelCount(); i++){
                        ModelBakery.addVariantName(Item.getItemFromBlock(epBlock.getBlock()), StaticFieldCollection.MODID + ":" + epBlock.getBlockName() + "_" + i);
                        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), i, new ModelResourceLocation(StaticFieldCollection.MODID + ":" + epBlock.getBlockName() + "_" + i, "inventory"));
                    }
                }
            }
        }
    }
}
