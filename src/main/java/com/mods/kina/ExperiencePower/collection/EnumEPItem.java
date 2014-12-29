package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.item.ItemExpSealableBook;
import net.minecraft.item.Item;

public enum EnumEPItem{
    ExperienceSealableBook(new ItemExpSealableBook()),;
    private Item item;
    EnumEPItem(Item item){
        this.item=item;
    }

    public Item getItem(){
        return item;
    }
}
