package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
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

import java.awt.*;
import java.util.List;

import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.burnedClayColor;
import static com.mods.kina.ExperiencePower.collection.ConfigurableFieldCollection.clayColor;

public class ItemMold extends ItemEPBase{
    public ItemMold(){
        setUnlocalizedName("mold");
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        //setHasSubtypes(true);
        setMaxStackSize(1);
    }

    public static ItemStack setNBT(ItemStack stack, String type, String metal, boolean smelted){
        NBTTagCompound tagCompound = stack.getSubCompound("cast", true);
        tagCompound.setString("type", type);
        tagCompound.setString("content", metal);
        tagCompound.setBoolean("smelted", smelted);
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
        if(renderPass == 0){
            return getNBT(stack).getBoolean("smelted") ? burnedClayColor : clayColor;
        }
        if(getNBT(stack) == null) return 0xffffff;
        if("empty".equals(getNBT(stack).getString("content")))
            return darknessColor(getNBT(stack).getBoolean("smelted") ? burnedClayColor : clayColor);
        if("Ingot".equals(getNBT(stack).getString("type")) && ("Iron".equals(getNBT(stack).getString("content")) || "Gold".equals(getNBT(stack).getString("content"))))
            return 0xFFFFFF;
        return EnumMetal.valueOf(getNBT(stack).getString("content")).getColor();
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, List list){
        for(Type type : Type.values()){
            for(EnumMetal metal : EnumMetal.values()){
                list.add(setNBT(new ItemStack(itemIn), type.name(), metal.name(), true));
            }
            list.add(setNBT(new ItemStack(itemIn), type.name(), "empty", false));
            list.add(setNBT(new ItemStack(itemIn), type.name(), "empty", true));
        }
    }

    public String getItemStackDisplayName(ItemStack stack)
    {
        return EnumEPItem.Cast.getItem().getItemStackDisplayName(stack)+g11nItem("mold");
    }

    public String getUnlocalizedName(ItemStack stack){
        if(getNBT(stack) == null) return super.getUnlocalizedName(stack);
        return super.getUnlocalizedName() + "_" + getNBT(stack).getString("type").toLowerCase() + "_" + getNBT(stack).getString("content").toLowerCase();
    }

    public ItemMeshDefinition getMeshDef(){
        ModelBakery.addVariantName(this, "kina_experiencepower:mold_ingot", "kina_experiencepower:mold_ingot_iron", "kina_experiencepower:mold_ingot_gold", "kina_experiencepower:mold_wire", "kina_experiencepower:mold_plate");
        return new ItemMeshDefinition(){
            public ModelResourceLocation getModelLocation(ItemStack stack){
                if("Ingot".equals(getNBT(stack).getString("type")) && ("Iron".equals(getNBT(stack).getString("content")) || "Gold".equals(getNBT(stack).getString("content")))){
                    return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + getUnlocalizedName(stack).replaceFirst("item\\.", "").replaceFirst("kina\\.", ""), "inventory");
                }
                return new ModelResourceLocation(StaticFieldCollection.MODID + ":" + getUnlocalizedName(stack).replaceFirst("item\\.", "").replaceFirst("kina\\.", "").replaceFirst("_" + getNBT(stack).getString("content").toLowerCase(), ""), "inventory");
            }
        };
    }

    private int darknessColor(int original){
        Color c = new Color(original);
        float[] hsb = Color.RGBtoHSB(c.getRed(), c.getGreen(), c.getBlue(), new float[3]);
        return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2] * 0.5f);
    }

    public ItemStack getContainerItem(ItemStack itemStack){
        itemStack.getSubCompound("cast", false).setString("content", "empty");
        return itemStack;
    }

    public boolean hasContainerItem(ItemStack stack){
        return !stack.getSubCompound("cast", false).getString("content").equals("empty");
    }

    public enum Type{
        Ingot,
        Wire,
        Plate
    }
}
