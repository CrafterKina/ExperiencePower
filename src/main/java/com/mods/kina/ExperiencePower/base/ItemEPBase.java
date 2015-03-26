package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemEPBase extends Item{
    public ItemEPBase(){
    }

    public String getUnlocalizedName(){
        return super.getUnlocalizedName().replaceFirst("item\\.", "kina.item.");
    }

    public String getUnlocalizedName(ItemStack stack){
        return getUnlocalizedName();
    }

    public ItemMeshDefinition getMeshDef(){
        return new ItemMeshDefinition(){
            public ModelResourceLocation getModelLocation(ItemStack stack){
                return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + getUnlocalizedName().substring(5).replaceFirst("item\\.", "").replaceFirst("kina\\.", ""), "inventory");
            }
        };
    }
}
