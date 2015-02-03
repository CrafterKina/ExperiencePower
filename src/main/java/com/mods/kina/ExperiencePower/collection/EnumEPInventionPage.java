package com.mods.kina.ExperiencePower.collection;

import com.mods.kina.ExperiencePower.invent.InventionPage;

public enum EnumEPInventionPage{
    Life(new InventionPage());
    private InventionPage page;

    EnumEPInventionPage(InventionPage page){

        this.page = page;
    }

    public InventionPage getPage(){
        return page;
    }
}
