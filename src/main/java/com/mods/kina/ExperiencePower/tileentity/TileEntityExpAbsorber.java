package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IChatComponent;

import java.util.List;

public class TileEntityExpAbsorber extends TileEntityMachineBase{

    /*
    空エンチャ本に詰めるためのスロット。
     */
    private ItemStack[] itemStacks=new ItemStack[2];


    @SuppressWarnings("unchecked")
    protected List<EntityPlayer> getOnPlayers() {
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        return worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb);
    }

    @SuppressWarnings("unchecked")
    protected List<EntityXPOrb> getOnXPOrbs() {
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1.3, pos.getZ() + 1);
        return worldObj.getEntitiesWithinAABB(EntityXPOrb.class, aabb);
    }

    /*IInventory*/

    /**
     ここでコンテナー内のアイテムを全部空にする処理を詰め込むっぽい。
     */
    public void clear(){

    }

    /**
     ローカライズ後の名前。
     */
    public IChatComponent getDisplayName(){
        return null;
    }
}
