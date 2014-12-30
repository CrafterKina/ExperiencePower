package com.mods.kina.ExperiencePower.register;

import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRegistrar{

    public static void registerBlock(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            GameRegistry.registerBlock(epBlock.getBlock(),epBlock.getBlock().getUnlocalizedName().substring(5));
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), 0, new ModelResourceLocation(StaticFieldCollection.MODID + ":" + epBlock.getBlock().getUnlocalizedName().substring(5), "inventory"));
        }
    }
}
