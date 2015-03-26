package com.mods.kina.ExperiencePower.collection;

import net.minecraft.item.Item;
import net.minecraftforge.common.util.EnumHelper;

public enum EnumEPToolMaterial{
    Iron(Item.ToolMaterial.IRON),
    Gold(Item.ToolMaterial.GOLD),
    Garbage(EnumHelper.addToolMaterial("kina_ep_garbage", 0, 0, 0, 0, 0)),
    Copper(EnumHelper.addToolMaterial("kina_ep_copper", 2, 131, 4, 1, 5)),
    Bronze(EnumHelper.addToolMaterial("kina_ep_bronze", 2, 360, 5, 1.5f, 14)),
    Wise(EnumHelper.addToolMaterial("kina_ep_wise", 2, 250, 12.0F, 0.0F, 22)),;

    private final Item.ToolMaterial toolMaterial;

    EnumEPToolMaterial(Item.ToolMaterial material){
        toolMaterial = material;
    }

    public static EnumEPToolMaterial getMaterialFromAbility(int harvestLevel, int maxUses, float efficiency, float damage, int enchantability){
        for(EnumEPToolMaterial material : EnumEPToolMaterial.values()){
            Item.ToolMaterial tm = material.getToolMaterial();
            if(tm.getHarvestLevel() != harvestLevel) continue;
            if(tm.getMaxUses() != maxUses) continue;
            if(tm.getEfficiencyOnProperMaterial() != efficiency) continue;
            if(tm.getDamageVsEntity() != damage) continue;
            if(tm.getEnchantability() != enchantability) continue;
            return material;
        }
        return Garbage;
    }

    public Item.ToolMaterial getToolMaterial(){
        return toolMaterial;
    }

}
