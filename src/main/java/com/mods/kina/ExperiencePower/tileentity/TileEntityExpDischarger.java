package com.mods.kina.ExperiencePower.tileentity;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.base.TileEntityMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.util.AxisAlignedBB;

public class TileEntityExpDischarger extends TileEntityMachineBase implements IUpdatePlayerListBox{

    public TileEntityExpDischarger(){
        super("exp_discharger", false, 1);
    }

    /**
     Updates the JList with a new model.
     */
    public void update(){
        EntityPlayer player = getPlayer();
        if(player != null && player.isSneaking() && canPrepareExperience()){
            player.addExperienceLevel(1);
            getStackInSlot(0).setItemDamage(getStackInSlot(0).getItemDamage() - 1);
        }
    }

    /**

     */
    private boolean canPrepareExperience(){
        return getStackInSlot(0).getItem().equals(EnumEPItem.ExperienceSealableBook.getItem()) && getStackInSlot(0).getItemDamage() > 0;
    }

    @SuppressWarnings("unchecked")
    protected EntityPlayer getPlayer(){
        AxisAlignedBB aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1);
        return !worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb).isEmpty() ? (EntityPlayer) worldObj.getEntitiesWithinAABB(EntityPlayer.class, aabb).get(0) : null;
    }

    /**
     Containerにスロットを追加したりする。 やろうと思えばなんでもできるが多分スロット追加だけ。
     */
    public void initContainer(ContainerMachineBase container){
        container.addSlotToContainer(new Slot(this, 0, 98, 53));
    }
}
