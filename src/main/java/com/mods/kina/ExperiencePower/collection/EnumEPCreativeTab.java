package com.mods.kina.ExperiencePower.collection;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public enum EnumEPCreativeTab{
    BLOCK(new CreativeTabs("ep_block"){
        public Item getTabIconItem(){
            return Item.getItemFromBlock(EnumEPBlock.ExperienceAbsorber.getBlock());
        }
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel()
        {
            return "ExperiencePower Block";
        }
    }),
    ITEM(new CreativeTabs("ep_item"){
        public Item getTabIconItem(){
            return Items.enchanted_book;
        }
        @SideOnly(Side.CLIENT)
        public String getTranslatedTabLabel()
        {
            return "ExperiencePower Item";
        }
    }),;

    private CreativeTabs creativeTab;

    EnumEPCreativeTab(CreativeTabs tab){
        creativeTab=tab;
    }

    public CreativeTabs getCreativeTab(){
        return creativeTab;
    }
}
