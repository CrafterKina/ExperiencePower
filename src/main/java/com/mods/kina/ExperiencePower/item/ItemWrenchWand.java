package com.mods.kina.ExperiencePower.item;

import com.mods.kina.ExperiencePower.base.IWrench;
import com.mods.kina.ExperiencePower.base.IWrenchable;
import com.mods.kina.ExperiencePower.base.IWrenchingInfo;
import com.mods.kina.ExperiencePower.base.ItemEPBase;
import com.mods.kina.ExperiencePower.collection.EnumEPCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemWrenchWand extends ItemEPBase implements IWrench{
    public ItemWrenchWand(){
        setUnlocalizedName("wrench_wand");
        setCreativeTab(EnumEPCreativeTab.ITEM.getCreativeTab());
        setMaxStackSize(1);
        setHasSubtypes(true);
        setFull3D();
    }


    public boolean onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ){
        return worldIn.getBlockState(pos).getBlock() instanceof IWrenchable && ((IWrenchable) worldIn.getBlockState(pos).getBlock()).wrench(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entity, int itemSlot, boolean isSelected){
        changeWrenchColor(worldIn, stack, entity, isSelected, false);
    }

    /**
     Called each tick while using an item.

     @param stack
     The Item being used
     @param player
     The Player using the item
     @param count
     The amount of time in tick the item has been used for continuously
     */
    public void onUsingTick(ItemStack stack, EntityPlayer player, int count){
        changeWrenchColor(player.worldObj, stack, player, true, true);
    }

    private void changeWrenchColor(World world, ItemStack stack, Entity entity, boolean isSelected, boolean isUsing){
        if(stack.getItemDamage()>0)stack.setItemDamage(0);
        setColor(stack,0xffffff);
        if(!isSelected) return;
        MovingObjectPosition rayTrace = entity.rayTrace((double) Minecraft.getMinecraft().playerController.getBlockReachDistance(), 1);
        if(rayTrace == null || rayTrace.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) return;
        if(!(world.getBlockState(rayTrace.getBlockPos()).getBlock() instanceof IWrenchingInfo)) return;
        IWrenchingInfo block = (IWrenchingInfo) world.getBlockState(rayTrace.getBlockPos()).getBlock();
        setColor(stack, block.getWrenchColor(world, stack, entity, rayTrace, isUsing));
    }

    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int renderPass){
        return renderPass > 0 ? 0xffffff : getColor(stack);
    }

    public int getColor(ItemStack stack){
        return stack.getSubCompound("wrench", false) == null ? 0xffffff : stack.getSubCompound("wrench", false).getInteger("color");
    }

    public ItemStack setColor(ItemStack stack, int color){
        stack.getSubCompound("wrench",true).setInteger("color",color);
        return stack;
    }

    public void getCreativeItemStacks(Item item, CreativeTabs tab, List<ItemStack> list){
        list.add(setColor(new ItemStack(item), 0xffffff));
    }
}
