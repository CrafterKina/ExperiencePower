package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemExpSealableBook extends ItemEPBase{
    public ItemExpSealableBook(){
        setUnlocalizedName("book_sealable");
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setMaxDamage(Short.MAX_VALUE);
    }

    //経験値が入っていれば光る
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack)
    {
        return stack.getMetadata()>0;
    }

    /**
     * Return an item rarity from EnumRarity
     * 経験値が入っていればアイテム名が黄文字に
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return stack.getMetadata()>0?EnumRarity.UNCOMMON:super.getRarity(stack);
    }
    /**
     * allows items to add custom lines of information to the mouseover description
     *
     * @param tooltip All lines to display in the Item's tooltip. This is a List of Strings.
     * @param advanced Whether the setting "Advanced tooltips" is enabled
     */
    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced)
    {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(stack.getMetadata()+"/"+(Short.MAX_VALUE-1));
    }
}
