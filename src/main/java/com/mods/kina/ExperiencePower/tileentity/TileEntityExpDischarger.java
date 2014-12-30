package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IChatComponent;

public class TileEntityExpDischarger extends TileEntityMachineBase implements IUpdatePlayerListBox{

    /**
     空エンチャ本に出すためのスロット。
     */
    private ItemStack[] itemStacks = new ItemStack[1];

    /**
     Updates the JList with a new model.
     */
    public void update(){
        EntityPlayer player = getPlayer();
        if(player != null && player.isSneaking() && canPrepareExperience()){
            player.addExperienceLevel(1);
        }
    }

    /**

     */
    private boolean canPrepareExperience(){
        return itemStacks[0].getItem().equals(EnumEPItem.ExperienceSealableBook.getItem());
    }

    @SuppressWarnings("unchecked")
    protected EntityPlayer getPlayer(){
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        return (EntityPlayer) worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb).get(0);
    }

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
