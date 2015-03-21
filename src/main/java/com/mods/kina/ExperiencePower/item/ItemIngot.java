package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.block.BlockOre;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemIngot extends ItemEPBase{
    public ItemIngot(){
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setHasSubtypes(true);
        setMaxDamage(BlockOre.OreType.values().length - 1);
        setNoRepair();
        setUnlocalizedName("ingot");
    }

    //鉱石から色を取得。
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass){
        Block block = EnumEPBlock.Ore.getBlock();
        return block.getRenderColor(block.getStateFromMeta(stack.getItemDamage()));
    }

    /**
     Converts the given ItemStack damage value into a metadata value to be placed in the world when this Item is placed
     as a Block (mostly used with ItemBlocks).
     */
    public int getMetadata(int damage){
        return damage;
    }

    public String getUnlocalizedName(ItemStack stack){
        return super.getUnlocalizedName() + "_" + BlockOre.OreType.values()[stack.getItemDamage()].getName();
    }

    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, List list){
        for(int i = 0; i < BlockOre.OreType.values().length; i++){
            list.add(new ItemStack(itemIn, 1, i));
        }
    }

    /**
     Determines if the durability bar should be rendered for this item. Defaults to vanilla stack.isDamaged behavior. But
     modders can use this for any data they wish.

     @param stack
     The current Item Stack
     @return True if it should render the 'durability' bar.
     */
    public boolean showDurabilityBar(ItemStack stack){
        return false;
    }
}
