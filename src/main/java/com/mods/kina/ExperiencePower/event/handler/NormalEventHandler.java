package com.mods.kina.ExperiencePower.event.handler;

import com.mods.kina.ExperiencePower.collection.EnumEPAchievement;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 MinecraftForge.EVENT_BUSのEvent。
 Normalと言うよりTerrainGenでないEvent。
 */
public class NormalEventHandler{
    /**
     実績解除時に経験値を獲得。
     */
    @SubscribeEvent
    public void onAchievementGet(AchievementEvent event){
        event.entityPlayer.addExperience(2);
    }

    /**
     葉っぱと作物から経験値が出る。
     */
    @SubscribeEvent
    public void getBlocksDrop(BlockEvent.HarvestDropsEvent event){
        IBlockState iBlockState = event.world.getBlockState(event.pos);
        if(iBlockState.getBlock() instanceof BlockLeaves && event.world.rand.nextInt(4) == 0){
            Block.spawnAsEntity(event.world, event.pos, new ItemStack(Items.stick));
        }else if(iBlockState.getBlock() instanceof BlockCrops && (Integer) iBlockState.getValue(BlockCrops.AGE) == 7){
            event.world.spawnEntityInWorld(new EntityXPOrb(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ(), event.world.rand.nextInt(6) + 1));
        }
    }

    /**
     鉱石取得時に実績解除
     */
    @SubscribeEvent
    public void onPickUpItem(EntityItemPickupEvent event){
        ItemStack stack = event.item.getEntityItem();
        if(stack.getItem().equals(Item.getItemFromBlock(EnumEPBlock.Ore.getBlock()))){
            if(stack.getMetadata() == 3)
                event.entityPlayer.triggerAchievement(EnumEPAchievement.ChunkOfKnowledge.getAchievement());
            else event.entityPlayer.triggerAchievement(EnumEPAchievement.NewOres.getAchievement());
        }
    }
}
