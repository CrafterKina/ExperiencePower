package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import com.mods.kina.ExperiencePower.collection.EnumEPInventionPage;
import com.mods.kina.ExperiencePower.gui.GuiInventionNote;
import com.mods.kina.ExperiencePower.invent.InventionElement;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemInventionNote extends Item{
    public ItemInventionNote(){
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setUnlocalizedName("invention_note");
    }

    /**
     発見ノート(仮称)のGUIを開く。
     */
    public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn){
        Minecraft.getMinecraft().displayGuiScreen(new GuiInventionNote(Minecraft.getMinecraft().currentScreen, itemStackIn));
        return itemStackIn;
    }

    /**
     "note"という特別のNBTTagCompoundを用意。
     */
    public NBTTagCompound getNBT(ItemStack stack){
        return stack.getSubCompound("note", true);
    }

    /**
     ページの要素を取り出す。 リストでの取得は今のところない。
     */
    public InventionElement getElementFromPage(int page, int invent){
        return EnumEPInventionPage.values()[page].getPage().getElements().get(invent);
    }

    /**
     ページの中での要素のIDを取得。
     もう少しスマートにしたい。
     */
    public int getIDFromPage(int page, InventionElement element){
        for(int i = 0; i < EnumEPInventionPage.values()[page].getPage().getElements().size(); i++){
            if(getElementFromPage(page, i).equals(element)) return i;
        }
        return -1;
    }
    
    /**
     この要素は確認済みなのかどうか。
     未発見・発見・発明の三段階に分けたほうがいいかも。
     */
    public boolean hasInventionUnlocked(List<List<Byte>> list, int page, int invent){
        return list.get(page).get(invent) == 1;
    }

    /**
     この要素を解除できるのか。
     */
    public boolean canUnlockInvention(List<List<Byte>> list, int page, int invent){
        return getElementFromPage(page, invent).parentAchievements == null || hasInventionUnlocked(list, page, invent);
    }

    /**
     returns a list of items with the same ID, but different meta (eg: dye returns 16 items) The List of sub-items. This
     is a List of ItemStacks.
     */
    @SuppressWarnings("unchecked")
    public void getSubItems(Item itemIn, CreativeTabs tab, List subItems){
        super.getSubItems(itemIn, tab, subItems);
        //subItems.add(initNBT(new ItemStack(itemIn)));
    }

    /**
     Called when item is crafted/smelted. Used only by maps so far.
     */
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn){
        //initNBT(stack);
    }

    /**
     NBTの初期化。
     */
    public void initNBT(NBTTagCompound tagCompound){
        NBTTagList pageList = new NBTTagList();
        for(int i = 0; i < EnumEPInventionPage.values().length; i++){
            NBTTagList inventionList = new NBTTagList();
            for(int j = 0; j < EnumEPInventionPage.values()[i].getPage().getElements().size(); j++){
                NBTTagByte unlocked = new NBTTagByte((byte) 0);
                inventionList.appendTag(unlocked);
            }
            pageList.appendTag(inventionList);
        }
        tagCompound.setTag("page", pageList);
    }

    /**
     親の一つでも解除後のものがあるか。
     */
    public int hasInventionUnlockedAnyone(List<List<Byte>> list, int page, InventionElement... elements){
        for(int i = 0; i < elements.length; i++){
            if(hasInventionUnlocked(list, page, getIDFromPage(page, elements[i]))) return i;
        }
        return -1;
    }

    /**
     解除済みのものからの要素の距離。
     */
    @SideOnly(Side.CLIENT)
    public int getDistanceFromOpened(List<List<Byte>> list, int page, int invent){
        if(hasInventionUnlocked(list, page, invent)){
            return 0;
        }else{
            int i = 0;

            for(InventionElement[] elements = getElementFromPage(page, invent).parentAchievements; elements.length < 0 && this.hasInventionUnlockedAnyone(list, page, elements) == -1; ++i){
                elements = elements[0].parentAchievements;
            }

            return i;
        }
    }
}
