package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.collection.EnumMetal;
import com.mods.kina.ExperiencePower.collection.StaticFieldCollection;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemCast extends ItemEPBase{
    public ItemCast(){
        setUnlocalizedName("cast");
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setHasSubtypes(true);
    }

    public static ItemStack setNBT(ItemStack stack, String type, String metal){
        NBTTagCompound tagCompound = stack.getSubCompound("cast", true);
        tagCompound.setString("type", type);
        tagCompound.setString("content", metal);
        return stack;
    }

    public static NBTTagCompound getNBT(ItemStack stack){
        NBTTagCompound tagCompound = stack.getSubCompound("cast", false);
        if(tagCompound == null) return null;
        return tagCompound;
    }

    //鉱石から色を取得。
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass){
        if(getNBT(stack) == null) return 0xffffff;
        return EnumMetal.valueOf(getNBT(stack).getString("content")).getColor();
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, List list){
        for(ItemMold.Type type : ItemMold.Type.values()){
            for(EnumMetal metal : EnumMetal.values()){
                if(type == ItemMold.Type.Ingot && (metal == EnumMetal.Iron || metal == EnumMetal.Gold)) continue;
                list.add(setNBT(new ItemStack(itemIn), type.name(), metal.name()));
            }
        }
    }

    public String getUnlocalizedName(ItemStack stack){
        if(getNBT(stack) == null) return super.getUnlocalizedName(stack);
        return super.getUnlocalizedName().replaceFirst("cast", getNBT(stack).getString("type").toLowerCase()) + "_" + getNBT(stack).getString("content").toLowerCase();
    }

    public ItemMeshDefinition getMeshDef(){
        ModelBakery.addVariantName(this, "kina_experiencepower:ingot", "kina_experiencepower:wire", "kina_experiencepower:plate");
        return new ItemMeshDefinition(){
            public ModelResourceLocation getModelLocation(ItemStack stack){
                return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + getUnlocalizedName(stack).replaceFirst("item\\.", "").replaceFirst("kina\\.", "").replaceFirst("_" + getNBT(stack).getString("content").toLowerCase(), ""), "inventory");
            }
        };
    }
}
