package com.mods.kina.ExperiencePower.event.handler;

import com.mods.kina.ExperiencePower.annotation.EPProp;
import com.mods.kina.ExperiencePower.collection.EnumEPAchievement;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import com.mods.kina.KinaCore.event.furnace.ItemSmeltEvent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 MinecraftForge.EVENT_BUSのEvent。
 Normalと言うよりTerrainGenでないEvent。
 */
public class NormalEventHandler{
    @EPProp
    public static final boolean enableGetExpOnAchievementGet = true;
    @EPProp
    public static final boolean enableDropStickFromLeaves = true;
    @EPProp
    public static final boolean enableDropExpFromCrops = true;
    /**
     実績解除時に経験値を獲得。
     */
    @SubscribeEvent
    public void onAchievementGet(AchievementEvent event){
        if(enableGetExpOnAchievementGet)
            if(!((EntityPlayerMP) event.entityPlayer).getStatFile().hasAchievementUnlocked(event.achievement))
                event.entityPlayer.addExperience(2);
    }

    /**
     葉っぱと作物から経験値が出る。
     */
    @SubscribeEvent
    public void getBlocksDrop(BlockEvent.HarvestDropsEvent event){
        IBlockState iBlockState = event.world.getBlockState(event.pos);
        if(iBlockState.getBlock() instanceof BlockLeaves && event.world.rand.nextInt(4) == 0){
            if(enableDropStickFromLeaves){
                Block.spawnAsEntity(event.world, event.pos, new ItemStack(Items.stick));
            }
        }else if(iBlockState.getBlock() instanceof BlockCrops && (Integer) iBlockState.getValue(BlockCrops.AGE) == 7){
            if(enableDropExpFromCrops){
                event.world.spawnEntityInWorld(new EntityXPOrb(event.world, event.pos.getX(), event.pos.getY(), event.pos.getZ(), event.world.rand.nextInt(6) + 1));
            }
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


    @SubscribeEvent
    public void onItemSmelt(ItemSmeltEvent.Pre e){
        if(e.getCookTime() != e.getTotalCookTime()) return;
        ItemStack result = FurnaceRecipes.instance().getSmeltingResult(e.getItem(0));
        if(!result.getItem().equals(EnumEPItem.Mold.getItem())) return;
        if(e.getItem(1) != null && EnumEPItem.Mold.getItem().equals(e.getItem(1).getItem())) return;
        e.setCookTime(e.getCookTime() - 1);
    }

    @SubscribeEvent
    public void onItemSmelted(ItemSmeltEvent.Post e){
        if(e.getItem(1) != null && e.getItem(1).getItem().equals(EnumEPItem.Mold.getItem()) && e.getItem(2).getItem().equals(EnumEPItem.Mold.getItem())){
            if(e.getItem(2).getSubCompound("cast", false).getString("content").equalsIgnoreCase("empty")) return;
            e.getItem(2).getSubCompound("cast", false).setString("type", e.getItem(1).getSubCompound("cast", false).getString("type"));
            e.getItem(2).getSubCompound("cast", false).setBoolean("smelted", true);
            e.setItem(1, null);
        }
    }
}
