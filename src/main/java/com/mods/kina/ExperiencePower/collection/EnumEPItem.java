package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.item.*;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;

public enum EnumEPItem{
    ExperienceSealableBook(new ItemExpSealableBook()),
    ExperienceCropSeed(new ItemSeeds(EnumEPBlock.ExperienceCrop.getBlock(), Blocks.farmland).setUnlocalizedName("exp_seed").setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab())),
    Ingot(new ItemIngot()),
    MonsterPlacer(new ItemEPMonsterPlacer()),
    Wrench(new ItemWrenchWand()),
    ArmorPump(new ItemArmorPump()),
    InventionNote(new ItemInventionNote());
    private Item item;
    EnumEPItem(Item item){
        this.item=item;
    }

    public Item getItem(){
        return item;
    }
}
