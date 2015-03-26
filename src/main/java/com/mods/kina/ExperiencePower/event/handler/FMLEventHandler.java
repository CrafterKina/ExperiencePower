package com.mods.kina.ExperiencePower.event.handler;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 FMLCommonHandler.instance().bus()とかいうやたら深いところのEvent。 TickEvent等の基本的なものが多い。
 */
public class FMLEventHandler{
    @SubscribeEvent
    public void onCrafted(PlayerEvent.ItemCraftedEvent e){
        /*for(int i = 0; i < e.craftMatrix.getSizeInventory(); i++){
            if(e.craftMatrix.getStackInSlot(i).getItem().equals(EnumEPItem.Mold.getItem())){
                e.craftMatrix.setInventorySlotContents(i,null);
            }
        }*/
    }
}
