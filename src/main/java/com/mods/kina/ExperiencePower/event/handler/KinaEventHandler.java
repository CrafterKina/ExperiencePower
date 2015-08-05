package com.mods.kina.ExperiencePower.event.handler;

import com.google.common.base.Objects;
import com.mods.kina.KinaCore.event.hooks.mod.ModStatusChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class KinaEventHandler{
    @SubscribeEvent
    public void onModStatusChange(ModStatusChangedEvent event){
        System.out.println(Objects.toStringHelper(this).addValue(event.prevStateMap).addValue(event.stateMap).addValue(event.mod).addValue(event.state));
    }
}
