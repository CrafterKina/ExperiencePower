package com.mods.kina.ExperiencePower.event.handler;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraftforge.event.terraingen.SaplingGrowTreeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 MinecraftForge.TERRAIN_GEN_BUSのEvent。
 ワールド生成関係のEvent。
 */
public class TerrainGenEventHandler{
    @SubscribeEvent
    public void onGrowTree(SaplingGrowTreeEvent event){
        event.world.spawnEntityInWorld(new EntityXPOrb(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ(), event.world.rand.nextInt(6) + 1));
    }
}
