package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityExpAbsorber extends TileEntityMachineBase implements IUpdatePlayerListBox{

    public TileEntityExpAbsorber(){
        super("exp_absorber", false, 1);
    }

    /**
     Updates the JList with a new model.
     */
    public void update(){
        EntityPlayer player = getOnPlayer();
        if(player != null && player.isSneaking() && getStackInSlot(0) != null && getStackInSlot(0).getItemDamage() < getStackInSlot(0).getMaxDamage()){
            player.removeExperienceLevel(1);
            getStackInSlot(0).setItemDamage(getStackInSlot(0).getItemDamage() + 1);
        }
    }

    @SuppressWarnings("unchecked")
    protected EntityPlayer getOnPlayer(){
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        return !worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb).isEmpty() ? (EntityPlayer) worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb).get(0) : null;
    }

    @SuppressWarnings("unchecked")
    protected EntityXPOrb getOnXPOrb(){
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 1.3, pos.getZ() + 1);
        return (EntityXPOrb) worldObj.getEntitiesWithinAABB(EntityXPOrb.class, aabb).get(0);
    }

    /**
     Containerにスロットを追加したりする。 やろうと思えばなんでもできるが多分スロット追加だけ。
     */
    public void initContainer(ContainerMachineBase container){
        container.addSlotToContainer(new Slot(this, 0, 98, 53));
    }
}
