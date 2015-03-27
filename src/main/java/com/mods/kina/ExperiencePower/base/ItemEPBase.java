package com.mods.kina.ExperiencePower.base;

import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

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

    protected final String i18n(String localized,boolean item){
        return "kina."+(item?"item.":"")+localized+".name";
    }

    protected final String l10n(String unlocalized){
        return StatCollector.translateToLocal(unlocalized);
    }

    private String removeEnclosure(String origin){
        return origin.replaceAll("\"","");
    }

    protected final String g11n(String raw){
        return removeEnclosure(l10n(i18n(raw, false)));
    }

    protected final String g11nItem(String raw){
        return removeEnclosure(l10n(i18n(raw, true)));
    }
}
