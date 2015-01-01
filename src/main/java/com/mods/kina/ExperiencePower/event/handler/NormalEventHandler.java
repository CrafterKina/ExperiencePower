package com.mods.kina.ExperiencePower.event.handler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 MinecraftForge.EVENT_BUSのEvent。
 Normalと言うよりTerrainGenでないEvent。
 */
public class NormalEventHandler{
    @SubscribeEvent
    public void onAchievementGet(AchievementEvent event){
        event.entityPlayer.addExperience(2);
    }

    @SubscribeEvent
    public void getBlocksDrop(BlockEvent.HarvestDropsEvent event){
        if(event.world.getBlockState(event.pos) instanceof BlockLeaves && event.world.rand.nextInt(4) == 0){
            Block.spawnAsEntity(event.world, event.pos, new ItemStack(Items.stick));
        }
    }
}
