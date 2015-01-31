package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;

public class ItemArmorPump extends ItemArmor{
    public ItemArmorPump(){
        super(EnumHelper.addArmorMaterial("PUMP", "", 33, new int[]{0, 8, 0, 0}, 0), 3, 1);
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setUnlocalizedName("pump");
    }

    /**
     Called to tick armor in the armor slot. Override to do something
     */
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack){
        if(world.isRemote && ((EntityPlayerSP) player).movementInput.jump){
            useJetpack(player);
        }
        if(player.isSneaking() && player.isInWater()){
            itemStack.damageItem(-1, player);
        }
    }

    public boolean useJetpack(EntityPlayer player){
        float power = 1.0F;

        int worldHeight = player.worldObj.getHeight();
        int maxFlightHeight = (int) (worldHeight / 1.28F);

        double y = player.posY;

        if(y > maxFlightHeight - 25){
            if(y > maxFlightHeight) y = maxFlightHeight;

            power = (float) (power * ((maxFlightHeight - y) / 25.0D));
        }

        double prevmotion = player.motionY;
        player.motionY = Math.min(player.motionY + power * 0.2F, 0.6000000238418579D);
        float maxHoverY = 0.0F;

        if(((EntityPlayerSP) player).movementInput.jump){
            maxHoverY = 0.1F;
        }

        if(player.isSneaking()){
            maxHoverY = -0.1F;

        }

        if(player.motionY > maxHoverY){
            player.motionY = maxHoverY;

            if(prevmotion > player.motionY) player.motionY = prevmotion;
        }

        player.fallDistance = 0.0F;
        player.distanceWalkedModified = 0.0F;

        return true;
    }
}
