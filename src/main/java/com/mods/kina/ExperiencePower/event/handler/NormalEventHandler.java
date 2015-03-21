package com.mods.kina.ExperiencePower.event.handler;

import com.mods.kina.ExperiencePower.annotation.EPProp;
import com.mods.kina.ExperiencePower.base.IWrench;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.collection.EnumEPAchievement;
import com.mods.kina.ExperiencePower.collection.EnumEPBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.event.entity.player.AchievementEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

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
        if(enableGetExpOnAchievementGet) event.entityPlayer.addExperience(2);
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
    @SideOnly(Side.CLIENT)
    public void onDrawBlockHighlight(DrawBlockHighlightEvent event){
        EntityPlayer player = event.player;
        ItemStack itemstack = event.currentItem;
        MovingObjectPosition target = event.target;

        if(player == null) return;
        if(itemstack == null || !(itemstack.getItem() instanceof IWrench)) return;
        if(target.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        if(!(player.worldObj.getBlockState(target.getBlockPos()).getBlock() instanceof IWrenchingInfo)) return;

        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.color(0.0F, 0.0F, 0.0F, 0.4F);
        GL11.glLineWidth(2.0F);
        GlStateManager.disableTexture2D();
        GlStateManager.depthMask(false);

        double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * event.partialTicks;
        double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * event.partialTicks;
        double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * event.partialTicks;

        ((IWrenchingInfo) player.worldObj.getBlockState(target.getBlockPos()).getBlock()).renderInfo(player.worldObj, target.getBlockPos(), d0, d1, d2);

        GlStateManager.depthMask(true);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onModelBake(ModelBakeEvent event){
        /*Map<ModelResourceLocation, IModel> stateModels = ObfuscationReflectionHelper.getPrivateValue(ModelLoader.class, event.modelLoader, "stateModels");
        for(Map.Entry<ModelResourceLocation, IModel> e:stateModels.entrySet()){
            //if(e.getKey().getResourceDomain().equals(StaticFieldCollection.MODID)
                FMLLog.info("呼ばれた");
                Map<ModelResourceLocation,ModelBlockDefinition> definition = ObfuscationReflectionHelper.getPrivateValue(ModelBakery.class,event.modelLoader,"blockDefinitions");
                e.setValue(new CombinationModel(definition.get(e.getKey()).getVariants(e.getKey().getVariant()),event.modelLoader));
            //}
        }*/
    }
}
