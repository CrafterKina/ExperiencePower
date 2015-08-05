package com.mods.kina.ExperiencePower.event.handler;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.EventBus;

import static net.minecraftforge.common.MinecraftForge.*;

/**
 ここから各メソッドに分ける。 分量少なければベタ書きでもいいかもしれない。
 */
public enum EventHandler{
    kina(new KinaEventHandler()),
    normal(new NormalEventHandler()),
    client(new ClientEventHandler()),
    terrainGen(new TerrainGenEventHandler(), TERRAIN_GEN_BUS),
    oreGen(new OreGenEventHandler(), ORE_GEN_BUS),
    fml(new FMLEventHandler(), FMLCommonHandler.instance().bus()),;

    private final Object instance;
    private final EventBus bus;

    EventHandler(Object instance){
        this(instance, EVENT_BUS);
    }

    EventHandler(Object instance, EventBus bus){
        this.instance = instance;
        this.bus = bus;
    }

    public static void register(){
        for(EventHandler handler : values()){
            handler.bus.register(handler.instance);
        }
    }
}
