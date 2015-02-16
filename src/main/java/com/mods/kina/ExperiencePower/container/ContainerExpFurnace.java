package com.mods.kina.ExperiencePower.container;

import com.mods.kina.ExperiencePower.base.ContainerMachineBase;
import com.mods.kina.ExperiencePower.collection.EnumEPItem;
import com.mods.kina.ExperiencePower.slot.SlotFiltered;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerExpFurnace extends ContainerMachineBase{
    private int field_178152_f;
    private int field_178153_g;
    private int field_178154_h;
    private int field_178155_i;

    public ContainerExpFurnace(EntityPlayer player, World world, int x, int y, int z){
        super(player, world, x, y, z);
        addSlotToContainer(new Slot(machineBase, 0, 56, 17));
        addSlotToContainer(new SlotFiltered(machineBase, 1, 56, 53, new ItemStack(EnumEPItem.ExperienceSealableBook.getItem())));
        addSlotToContainer(new SlotFurnaceOutput(player, machineBase, 2, 116, 35));
    }

    /**
     Add the given Listener to the list of Listeners. Method name is for legacy.
     */
    public void addCraftingToCrafters(ICrafting listener){
        super.addCraftingToCrafters(listener);
        listener.func_175173_a(this, machineBase);
    }

    /**
     Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges(){
        super.detectAndSendChanges();

        for(Object crafter : this.crafters){
            ICrafting icrafting = (ICrafting) crafter;

            if(this.field_178152_f != machineBase.getField(2)){
                icrafting.sendProgressBarUpdate(this, 2, machineBase.getField(2));
            }

            if(this.field_178154_h != machineBase.getField(0)){
                icrafting.sendProgressBarUpdate(this, 0, machineBase.getField(0));
            }

            if(this.field_178155_i != machineBase.getField(1)){
                icrafting.sendProgressBarUpdate(this, 1, machineBase.getField(1));
            }

            if(this.field_178153_g != machineBase.getField(3)){
                icrafting.sendProgressBarUpdate(this, 3, machineBase.getField(3));
            }
        }

        this.field_178152_f = machineBase.getField(2);
        this.field_178154_h = machineBase.getField(0);
        this.field_178155_i = machineBase.getField(1);
        this.field_178153_g = machineBase.getField(3);
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        machineBase.setField(id, data);
    }

    public boolean canInteractWith(EntityPlayer playerIn){
        return machineBase.isUseableByPlayer(playerIn);
    }

    /**
     Take a stack from the specified inventory slot.
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index){
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(index);

        if(slot != null && slot.getHasStack()){
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if(index == 2){
                if(!this.mergeItemStack(itemstack1, 3, 39, true)){
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }else if(index != 1 && index != 0){
                if(FurnaceRecipes.instance().getSmeltingResult(itemstack1) != null){
                    if(!this.mergeItemStack(itemstack1, 0, 1, false)){
                        return null;
                    }
                }else if(EnumEPItem.ExperienceSealableBook.getItem().equals(itemstack1.getItem())){
                    if(!this.mergeItemStack(itemstack1, 1, 2, false)){
                        return null;
                    }
                }else if(index >= 3 && index < 30){
                    if(!this.mergeItemStack(itemstack1, 30, 39, false)){
                        return null;
                    }
                }else if(index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)){
                    return null;
                }
            }else if(!this.mergeItemStack(itemstack1, 3, 39, false)){
                return null;
            }

            if(itemstack1.stackSize == 0){
                slot.putStack(null);
            }else{
                slot.onSlotChanged();
            }

            if(itemstack1.stackSize == itemstack.stackSize){
                return null;
            }

            slot.onPickupFromSlot(playerIn, itemstack1);
        }

        return itemstack;
    }
}
