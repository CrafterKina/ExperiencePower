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
    //EnumからBlockを取得、登録している。
    public static void registerBlock(){
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            GameRegistry.registerBlock(epBlock.getBlock(), epBlock.getItemBlock(), epBlock.getBlockName(), epBlock.getItemConstructorsArgs());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerModel(){
        //EnumEPBlockに入っているBlockを
        for(EnumEPBlock epBlock:EnumEPBlock.values()){
            //もしModelがひとつしかないなら
            if(epBlock.getModelCount() == 1 && epBlock.getModelNames() == null){
                //そのまま登録
                Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), 0, new ModelResourceLocation(StaticFieldCollection.MODID + ":" + epBlock.getBlockName(), "inventory"));
            }else{//一つ以上あるなら
                //もしModelNameが指定してあったら
                if(epBlock.getModelNames() != null && epBlock.getModelNames().length > 0){
                    //その名で登録
                    ModelBakery.addVariantName(Item.getItemFromBlock(epBlock.getBlock()), epBlock.getModelNames());
                    for(int i = 0; i < epBlock.getModelCount(); i++){
                        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), i, new ModelResourceLocation(epBlock.getModelNames()[i], "inventory"));
                    }
                }else{//そうでなかったら
                    //ポストフィクスに数値を指定し登録。
                    for(int i = 0; i < epBlock.getModelCount(); i++){
                        ModelBakery.addVariantName(Item.getItemFromBlock(epBlock.getBlock()), StaticFieldCollection.MODID + ":" + epBlock.getBlockName() + "_" + i);
                        Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(Item.getItemFromBlock(epBlock.getBlock()), i, new ModelResourceLocation(StaticFieldCollection.MODID + ":" + epBlock.getBlockName() + "_" + i, "inventory"));
                    }
                }
            }
        }
    }
}
