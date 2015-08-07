package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.item.*;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;

public enum EnumEPItem{
    //ExperienceSealableBook(new ItemExpSealableBook()),
    ExperienceCropSeed(new ItemSeeds(EnumEPBlock.ExperienceCrop.getBlock(), Blocks.farmland).setUnlocalizedName("exp_seed").setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab())),
    //Ingot(new ItemIngot()),
    //MonsterPlacer(new ItemEPMonsterPlacer()),
    Wrench(new ItemWrenchWand()),
    ArmorPump(new Item/*ArmorPump*/().setUnlocalizedName("pump")),
    InventionNote(new ItemEPBase/*InventionNote*/().setUnlocalizedName("invention_note")),
    Mold(new ItemMold()),
    Cast(new ItemCast()),
    MemoryBook(new ItemBlockMemoryBook()),
    MixingBlade(new ItemSeparateBlade()),;

    private Item item;

    EnumEPItem(Item item){
        this.item = item;
    }

    public Item getItem(){
        return item;
    }

    public ItemMeshDefinition getMeshDef(){
        if(item instanceof ItemEPBase){
            return ((ItemEPBase) item).getMeshDef();
        }

        return new ItemMeshDefinition(){
            public ModelResourceLocation getModelLocation(ItemStack stack){
                return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + item.getUnlocalizedName().substring(5).replaceFirst("item\\.", "").replaceFirst("kina\\.", ""), "inventory");
            }
        };
    }
}
