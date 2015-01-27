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
            player.motionY += 0.075;
            itemStack.damageItem(1, player);
            player.fallDistance = 0;
        }
        if(player.isSneaking() && player.isInWater()){
            itemStack.damageItem(-1, player);
        }
    }
}
